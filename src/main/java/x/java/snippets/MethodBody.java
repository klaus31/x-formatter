package x.java.snippets;

import org.antlr.v4.runtime.tree.ParseTree;
import x.java.IndentService;
import x.java.JavaConfig;
import x.java.NodeWrapper;

import static java.util.Arrays.asList;

class MethodBody extends SimpleNodesJavaCodeSnippet {

    private final IndentService indentService;

    public MethodBody(IndentService indentService) {
        super(indentService);
        this.indentService = indentService;
    }

    @Override
    protected String toSourceString(NodeWrapper node) {
        StringBuilder result = new StringBuilder();
        result.append(node.toSourceString());
        if (node.isBlockStartOrEnd() || node.isSemicolonAtEnd() || ":".equals(node.toSourceString())) {
            if (node.isNextNodeACommentInSameLine() || node.isBlockEnd() && node.isNextNodeElseCatchOrWhile()) {
                result.append(" ");
            } else {
                result.append(JavaConfig.EOL);
                result.append(indentService.calculateIndentToAppendTo(node));
            }
        } else if (requiresWhitespaceAfter(node)) {
            result.append(" ");
        }
        return result.toString();
    }

    private boolean requiresWhitespaceAfter(NodeWrapper node) {
        if (node.isSemicolonInBasicForStatement()) {
            return true;
        }
        if (asList(";", "(", ".", "++", "--").contains(node.toSourceString())) {
            return false;
        }
        if (asList("new", ",").contains(node.toSourceString())) {
            return true;
        }
        ParseTree nextNode = node.calculateNext();
        if ("(".equals(nextNode.getText())) {
            return asList("catch", "switch", "if", "for", "while", "+", "-", "*", "/", "%").contains(node.toSourceString());
        }
        if (asList(";", ":", ")", ",", ".", "++", "--").contains(nextNode.getText())) {
            return false;
        }
        if (")".equals(node.toSourceString())) {
            return !asList(".").contains(nextNode.getText());
        }
        if (node.matchesRulePath("unannClassType_lfno_unannClassOrInterfaceType")) {
            if (asList("<", ",", ">").contains(nextNode.getText())) {
                return false;
            } else if (node.toSourceString().equals(",")) {
                return true;
            } else if (node.toSourceString().equals("<")) {
                return false;
            } else {
                return true;
            }
        }
        if (node.matchesRulePath("classInstanceCreationExpression_lfno_primary")) {
            return false; // expecting diamond operator
        }
        return true;
    }
}