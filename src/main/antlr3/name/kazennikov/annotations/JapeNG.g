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
    OR;
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

rule: 'Rule:' SIMPLE matcher '-->' actions;
matcher: group modif? -> ^(GROUP_MATCHER modif? group);
group: '(' group_elem+ ('|' group_elem+)*')' -> ^(OR group_elem+);

simple_matcher: '{'! annot_spec (','! annot_spec)* '}'!;
annot_spec: '!'? type=SIMPLE ('.'! SIMPLE (op^ val)?)?;
val: SIMPLE | STRING | DIGITS;
op: '!=' -> ^(OP["neq"])
  | '==' -> ^(OP["eq"]);


group_elem: matcher | simple_matcher;

modif: (':' SIMPLE) -> ^(GROUP_OP["named"] SIMPLE)
        | '?' -> ^(GROUP_OP["?"])
        | '*' -> ^(GROUP_OP["*"])
        | '+' -> ^(GROUP_OP["+"])
        | '[' DIGITS (',' DIGITS)? ']' -> ^(GROUP_OP["range"] DIGITS+)
        ;
actions: (labelings|java_code)+;
java_code: '{' (SIMPLE | DIGITS | STRING | '(' | ')' | ',' | '.' | '<' | '>' | '[' | ']' | ':' | '=' | '!=' | '+' | '!' | java_code)* '}';
labelings: labeling (',' labeling)* -> labeling+;
labeling: ':' SIMPLE '.' SIMPLE '=' '{' attrvalue (',' attrvalue )'}';
attrvalue: SIMPLE | STRING '=' attrval;
attrval: SIMPLE | STRING | ':' SIMPLE '.' SIMPLE '.' SIMPLE;
        
          

WS: (' ' | '\t' | '\n' | '\r')+ { $channel = HIDDEN;};
SINGLE_COMMENT: '//' ~('\r' | '\n')* {$channel = HIDDEN;};
COMMENT:   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;};

STRING : '"' (~('"' | '\\') | '\\' .)* '"';
DIGITS: '0'..'9'+;
SIMPLE: ~('(' | ')' | ' ' | ',' | '.' | '<' | '>' | '\t' | '\r' | '\n' | '{' | '}' | '[' | ']' | ':')+;

