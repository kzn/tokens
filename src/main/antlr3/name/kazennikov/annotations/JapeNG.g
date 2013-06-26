grammar JapeNG;

options {
  language = Java;
  output = AST;
  ASTLabelType = CommonTree;
}

tokens {
    INPUT;
    PHASE;
    OPTIONS;
    OPTION;
}


@header {
  package name.kazennikov.annotations;
}

@lexer::header {
  package name.kazennikov.annotations;
}


jape: phase input opts rule+;
phase: 'Phase:' SIMPLE -> ^(PHASE SIMPLE);
input: 'Input:' SIMPLE+ -> ^(INPUT SIMPLE+);
opts: 'Options:' option (',' option)* -> ^(OPTIONS option+);
option: SIMPLE '=' SIMPLE -> ^(OPTION SIMPLE SIMPLE);

rule: rule_header matcher_group;
rule_header: 'Rule:' SIMPLE;
matcher_element: '{' SIMPLE ('.' SIMPLE)? (op (SIMPLE|STRING))? '}';
op: '!=' | '==' ;
matcher_group: '(' (matcher_group | matcher_element)+ ')' ((':' SIMPLE) | '?' | '*' | '+');

WS: (' ' | '\t' | '\n' | '\r')+ { $channel = HIDDEN;};
SINGLE_COMMENT: '//' ~('\r' | '\n')* {$channel = HIDDEN;};
COMMENT:   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;};

STRING : '"' (~('"' | '\\') | '\\' .)* '"';
SIMPLE: ~('(' | ')' | ' ' | ',' | '<' | '>' | '\t' | '\r' | '\n' | '{' | '}' | '[' | ']' )+;

