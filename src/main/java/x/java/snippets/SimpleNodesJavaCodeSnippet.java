package x.java.snippets;

import x.ctrl.SourceCodeFile;
import x.java.IndentService;
import x.java.NodeWrapper;
import java.util.ArrayList;
import java.util.List;
import static x.ctrl.MiserableLogger.logDebug;
import static x.java.JavaConfig.getIndentService;

public abstract class SimpleNodesJavaCodeSnippet implements JavaCodeSnippet {

    private final List<NodeWrapper> nodesInSnippet;
    private List<NodeWrapper> allNodesInCompilationUnit;

    SimpleNodesJavaCodeSnippet() {
        nodesInSnippet = new ArrayList<>();
    }

    String indentCurrent(SourceCodeFile file) {
        return getIndentService(file).getCurrentIndent();
    }

    @Override
    public String toSourceString(SourceCodeFile file) {
        if (nodesInSnippet.isEmpty()) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        for (NodeWrapper node : nodesInSnippet) {
            logDebug(node.toString());
            builder.append(toSourceString(node, file));
        }
        builder.append(afterSnippet(nodesInSnippet, allNodesInCompilationUnit, getIndentService(file)));
        return builder.toString();
    }

    protected abstract String afterSnippet(List<NodeWrapper> nodesInSnippet, List<NodeWrapper> allNodesInCompilationUnit, IndentService indentService);

    List<NodeWrapper> getAllNodesInCompilationUnit() {
        return allNodesInCompilationUnit;
    }

    protected abstract String toSourceString(NodeWrapper node, SourceCodeFile file);

    @Override
    public void add(NodeWrapper node, List<NodeWrapper> allNodesInCompilationUnit) {
        this.allNodesInCompilationUnit = allNodesInCompilationUnit;
        nodesInSnippet.add(node);
    }
}