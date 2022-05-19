package org.zsz.algorithms.support.printer;

/**

             ┌──800
         ┌──760
         │   └──600
     ┌──540
     │   └──476
     │       └──445
 ┌──410
 │   └──394
381
 │     ┌──190
 │     │   └──146
 │  ┌──40
 │  │  └──35
 └──12
    └──9

 *
 */
public class InorderPrinter extends Printer {
	private static final String RIGHT_APPEND;
	private static final String LEFT_APPEND;
	private static final String BLANK_APPEND;
	private static final String LINE_APPEND;

	static {
		int length = 2;
		RIGHT_APPEND = "┌" + Strings.repeat("─", length);
		LEFT_APPEND = "└" + Strings.repeat("─", length);
		BLANK_APPEND = Strings.blank(length + 1);
		LINE_APPEND = "│" + Strings.blank(length);
	}

	public InorderPrinter(BinaryTreeInfo tree) {
		super(tree);
	}

	@Override
	public String printString() {
		StringBuilder string = new StringBuilder(
				printString(tree.root(), "", "", ""));
		string.deleteCharAt(string.length() - 1);
		return string.toString();
	}
	
	/**
	 * 生成node节点的字符串
	 * @param nodePrefix node那一行的前缀字符串
	 * @param leftPrefix node整棵左子树的前缀字符串
	 * @param rightPrefix node整棵右子树的前缀字符串
	 * @return
	 */
	private String printString(
			Object node, 
			String nodePrefix,
			String leftPrefix, 
			String rightPrefix) {
		Object left = tree.left(node);
		Object right = tree.right(node);
		String string = tree.string(node).toString();
		
		int length = string.length();
		if (length % 2 == 0) {
			length--;
		}
		length >>= 1;
		
		String nodeString = "";
		if (right != null) {
			rightPrefix += Strings.blank(length);
			nodeString += printString(right, 
					rightPrefix + RIGHT_APPEND,
					rightPrefix + LINE_APPEND,
					rightPrefix + BLANK_APPEND);
		}
		nodeString += nodePrefix + string + "\n";
		if (left != null) {
			leftPrefix += Strings.blank(length);
			nodeString += printString(left, 
					leftPrefix + LEFT_APPEND,
					leftPrefix + BLANK_APPEND,
					leftPrefix + LINE_APPEND);
		}
		return nodeString;
	}
}