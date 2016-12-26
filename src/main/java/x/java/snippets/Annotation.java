package x.java.snippets;

import x.java.JavaConfig;
import x.java.JavaRulePath;
import x.java.NodeWrapper;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class Annotation extends SimpleNodesJavaCodeSnippet {

    @Override
    public String toSourceString() {
        return super.toSourceString() + JavaConfig.EOL;
    }

    @Override
    protected String toSourceString(NodeWrapper node) {
        final StringBuilder result = new StringBuilder();
        result.append(node.toSourceString());
        if (node.toSourceString().equals(",")) {
            result.append(" ");
        }
        return result.toString();
    }
}
