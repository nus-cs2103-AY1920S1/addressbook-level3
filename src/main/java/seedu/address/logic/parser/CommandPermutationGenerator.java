//package seedu.address.logic.parser;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//public class CommandPermutationGenerator {
//
//
//    public CommandPermutationGenerator(String commandWord, Prefix... prefixes) {
//
//    }
//
//    private static class PrefixOrderPermutation {
//        List<String> permutations;
//        List<Prefix> prefixes;
//        public PrefixOrderPermutation(List<Prefix> prefixes) {
//            prefixes.size();
//            permutations = new ArrayList<>();
//            permutate("", IntStream.range(0, prefixes.size())
//                    .mapToObj(Integer::toString)
//                    .reduce(String::concat)
//                    .orElse(""));
//        }
//
//        private void permutate(String previousPermutation, String remainder) {
//            if (remainder.length() <= 1) {
//                permutations.add(previousPermutation + remainder);
//            }
//            for (Character c : remainder.toCharArray()) {
//                permutate(previousPermutation + c.toString(),
//                        remainder.replaceFirst(c.toString(), ""));
//            }
//        }
//
//        public List<List<Prefix>> prefixOrderPermutations() {
//            return permutations.stream().map(x -> {
//                List<Prefix> permutation = new ArrayList<>();
//                for(Character c : x.toCharArray()) {
//                    permutation.add(prefixes.get(Integer.parseInt(c.toString())));
//                }
//                return permutation;
//            }).collect(Collectors.toList());
//        }
//    }
//
//    private static class PermutationTree<T> {
//        PermutationTree<T> root;
//        T node;
//        List<PermutationTree<T>> children;
//
//        private PermutationTree(PermutationTree<T> parent, T node, List<PermutationTree<T>> children) {
//            this.node = node;
//            this.root = parent;
//            this.children = children;
//        }
//
//        public static <E> PermutationTree<E> permutate(List<E> elements) {
//            PermutationTree<E> root = new PermutationTree<>(null, null, null);
//            permutate(root, elements);
//        }
//
//        //           null
//        //    /       |         |        \
//        //   p1       p2        p3        p4
//        //p2,p3,p4  p1,p3,p4  p1,p2,p4   p1,p2,p3
//        //
//        private static <E> void permutate(PermutationTree<E> tree, List<E> remaining) {
//            if (remaining.size() == 0) {
//                return;
//            }
//            List<PermutationTree<E>> children = new ArrayList<>();
//            for (E e : remaining) {
//                PermutationTree<E> t = new PermutationTree<E>(tree, e, null);
//                children.add(t);
//                List<E> rest = new ArrayList<>(remaining);
//                rest.remove(e);
//                permutate(t, rest);
//            }
//        }
//
//    }
//
//}
