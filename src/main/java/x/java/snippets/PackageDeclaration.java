package x.java.snippets;

import x.java.IndentService;
import x.java.JavaConfig;
import x.java.NodeWrapper;

public class PackageDeclaration extends SimpleNodesJavaCodeSnippet {

    PackageDeclaration(IndentService indentService) {
        super(indentService);
    }

    @Override
    public String toSourceString() {
        return super.toSourceString() + JavaConfig.EOL;
    }

    @Override
    protected String toSourceString(NodeWrapper node) {
        final StringBuilder result = new StringBuilder();
        result.append(node.toSourceString());
        if (node.toSourceString().equals("package")) {
            result.append(" ");
        }
        return result.toString();
    }
}
