import java.nio.file.Files;
import java.nio.file.Paths;

public class test {
	public static void main(String[] args) throws Exception {
		// **1. test word: axabxbxba **//
		suffixTree tree = new suffixTree();
		String t = "axabxbxba";
		tree.build(t);
		System.out.println(tree.containsSuffix("ba"));

		// //**2.test: text **//
		String data = readFileAsString("/Users/liweichen/Desktop/doc.txt");

		suffixTree tree2 = new suffixTree();
		tree2.build(data);
		System.out.println(tree2.containsSuffix("suffix trees."));
		System.out.println(tree2.containsSuffix("suffix"));
		

	}

	private static String readFileAsString(String fileName) throws Exception {
		return new String(Files.readAllBytes(Paths.get(fileName)));
	}

}
