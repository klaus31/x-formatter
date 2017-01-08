package x.java.snippets;

import x.java.JavaConfig;
import x.java.NodeWrapper;
import static java.util.Arrays.asList;
import static x.java.JavaConfig.*;

public class ClassDeclaration extends SimpleNodesJavaCodeSnippet {
    @Override
    protected String toSourceString(NodeWrapper node) {
        StringBuilder builder = new StringBuilder();
        builder.append(node.toSourceString());
        if (getEolService().requiresSingleEolAfterNodeInAnyCase(node).orElse(false)) {
            builder.append(EOL);
            builder.append(getIndentService().calculateIndentToAppendTo(node));
        } else if (getBlankService().requiresSingleBlankAfterNodeInAnyCase(node).orElse(true)) {
            builder.append(" ");
        }
        return builder.toString();
    }
}