package budgetbuddy.storage.scripts;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.commons.exceptions.DataConversionException;
import budgetbuddy.commons.util.JsonUtil;
import budgetbuddy.model.ScriptLibrary;
import budgetbuddy.model.ScriptLibraryManager;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.script.Script;
import budgetbuddy.model.script.ScriptName;
import budgetbuddy.storage.scripts.exceptions.ScriptsStorageException;

/**
 * Stores scripts as separate files in a directory.
 */
public class FlatfileScriptsStorage implements ScriptsStorage {
    private static final String DESCRIPTIONS_PATH = "descriptions.json";
    private static final Logger logger = LogsCenter.getLogger(FlatfileScriptsStorage.class);
    private static final FilenameFilter scriptFilenameFilter = (file, name) ->
            name.toLowerCase().endsWith(".js")
                    && ScriptName.isValidName(name.substring(0, name.length() - 3));

    private Path scriptsPath;

    /**
     * Constructs a flatfile script storage that stores scripts at the specified path.
     *
     * @param scriptsPath the path at which to store scripts
     */
    public FlatfileScriptsStorage(Path scriptsPath) {
        this.scriptsPath = scriptsPath;
    }

    @Override
    public Path getScriptsPath() {
        return scriptsPath;
    }

    @Override
    public ScriptLibrary readScripts(Path scriptsPath) throws IOException, ScriptsStorageException {
        File scriptsDir = scriptsPath.toFile();
        if (!scriptsDir.isDirectory()) {
            throw new ScriptsStorageException("Scripts path does not point to a directory");
        }

        Path scriptDescriptionsJson = scriptsPath.resolve(DESCRIPTIONS_PATH);
        Map<ScriptName, Description> descMap;
        try {
            descMap =
                    JsonUtil.readJsonFile(scriptDescriptionsJson, JsonSerializableScriptDescriptionMap.class)
                            .map(JsonSerializableScriptDescriptionMap::getDescriptionMap).orElse(null);
        } catch (DataConversionException e) {
            logger.warning(String.format(
                    "Exception while reading scripts description file; using blank descriptions. %s", e));
            descMap = null;
        }

        File[] scriptFiles = scriptsDir.listFiles(scriptFilenameFilter);

        ArrayList<Script> scripts = new ArrayList<>();
        for (File scriptFile : scriptFiles) {
            String scriptCode = Files.readString(scriptFile.toPath(), StandardCharsets.UTF_8);
            String scriptFileName = scriptFile.getName();
            ScriptName scriptName = scriptFileNameToScriptName(scriptFileName);

            Description scriptDesc = null;
            if (descMap != null) {
                scriptDesc = descMap.get(scriptName);
            }
            if (scriptDesc == null) {
                scriptDesc = new Description("");
            }

            scripts.add(new Script(scriptName, scriptDesc, scriptCode));
        }

        return new ScriptLibraryManager(scripts);
    }

    @Override
    public void saveScripts(ScriptLibrary scripts, Path scriptsPath) throws IOException {
        File scriptsDir = scriptsPath.toFile();
        if (scriptsDir.exists() && !scriptsDir.isDirectory() && !scriptsDir.delete()) {
            throw new IOException("Failed to delete non-directory at scripts path");
        }
        if (!scriptsDir.exists() && !scriptsDir.mkdirs()) {
            throw new IOException("Failed to create scripts directory");
        }

        Set<Path> existingScriptFiles =
                Arrays.stream(scriptsDir.listFiles(scriptFilenameFilter)).map(File::toPath)
                        .collect(Collectors.toSet());

        for (Script script : scripts.getScriptList()) {
            String scriptFileName = scriptToScriptFileName(script);
            Path scriptFilePath = scriptsPath.resolve(scriptFileName);
            Files.writeString(scriptFilePath, script.getCode(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            existingScriptFiles.remove(scriptFilePath);
        }

        for (Path leftoverScriptFile : existingScriptFiles) {
            Files.delete(leftoverScriptFile);
        }

        Path scriptDescriptionsJson = scriptsPath.resolve(DESCRIPTIONS_PATH);
        JsonSerializableScriptDescriptionMap jsonDescMap = new JsonSerializableScriptDescriptionMap(scripts);
        JsonUtil.saveJsonFile(jsonDescMap, scriptDescriptionsJson);
    }

    private static ScriptName scriptFileNameToScriptName(String scriptFileName) {
        return new ScriptName(scriptFileName.substring(0, scriptFileName.length() - 3));
    }

    private static String scriptToScriptFileName(Script script) {
        return String.format("%s.js", script.getName());
    }
}
