package x.java.snippets;

import x.java.IndentService;
import x.java.JavaRulePath;

public class CompilationUnit extends DecoratedJavaCodeSnippet {

    private final IndentService indentService;

    public CompilationUnit(IndentService indentService) {
        this.indentService = indentService;
    }

    @Override
    public void enterRule(JavaRulePath rulePath) {
        if (rulePath.isCurrentRuleA("packageDeclaration")) {
            setCurrentCodeSnippet(new PackageDeclaration(indentService));
        } else if (rulePath.isCurrentRuleA("importDeclaration")) {
            setCurrentCodeSnippet(new ImportDeclaration(indentService));
        } else if (rulePath.isCurrentRuleA("typeDeclaration")) {
            setCurrentCodeSnippet(new TypeDeclaration(indentService));
        }
        withCurrentSnippetIfPresent(s -> s.enterRule(rulePath));
    }
}