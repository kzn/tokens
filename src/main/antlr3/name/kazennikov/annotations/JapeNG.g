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
    MATCHER;
    GROUP_MATCHER;
    ANNOT;
    OP;
    GROUP_OP;
    ANNOT;
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

rule: 'Rule:' SIMPLE matcher;
matcher: group modif? -> ^(GROUP_MATCHER modif? group);
group: '('! group_elem+ ('|'^ group_elem+)*')'!;

simple_matcher: '{'! annot_spec (','! annot_spec)* '}'!;
annot_spec: '!'? type=SIMPLE ('.'! SIMPLE (op^ val)?)?;
val: SIMPLE | STRING;
op: '!=' -> ^(OP["neq"])
  | '==' -> ^(OP["eq"]);


group_elem: matcher | simple_matcher;

modif: (':' SIMPLE) -> ^(GROUP_OP["named"] SIMPLE)
        | '?' -> ^(GROUP_OP["?"])
        | '*' -> ^(GROUP_OP["*"])
        | '+' -> ^(GROUP_OP["+"])
        | '[' DIGITS (',' DIGITS)? ']' -> ^(GROUP_OP["range"] DIGITS+)
        ;
        
          

WS: (' ' | '\t' | '\n' | '\r')+ { $channel = HIDDEN;};
SINGLE_COMMENT: '//' ~('\r' | '\n')* {$channel = HIDDEN;};
COMMENT:   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;};

STRING : '"' (~('"' | '\\') | '\\' .)* '"';
DIGITS: '0'..'9'+;
SIMPLE: ~('(' | ')' | ' ' | ',' | '.' | '<' | '>' | '\t' | '\r' | '\n' | '{' | '}' | '[' | ']' | ':')+;

