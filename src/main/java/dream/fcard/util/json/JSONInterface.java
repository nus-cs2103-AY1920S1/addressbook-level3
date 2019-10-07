package dream.fcard.util.json;

import dream.fcard.util.json.jsontypes.JSONValue;

public interface JSONInterface {

    /**
     * Returns JSONValue representation of this object.
     * @return  JSONValue
     */
    JSONValue toJSON();
}
