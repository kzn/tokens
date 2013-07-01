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
matcher_element: '{' annot  
                  (op val -> ^(MATCHER op annot val)
                  | -> ^(MATCHER annot)) '}';

val: SIMPLE | STRING;
annot: SIMPLE ('.' SIMPLE)? -> ^(ANNOT SIMPLE*);
op: '!=' -> ^(OP["neq"])
  | '==' -> ^(OP["eq"]);


matcher_group: group group_op? -> ^(GROUP_MATCHER group_op? group);
group: '('! group_elem+ ('|'^ group_elem+)*')'!;

group_elem: (matcher_group | matcher_element);

group_op: (':' SIMPLE) -> ^(GROUP_OP["named"] SIMPLE)
        | '?' -> ^(GROUP_OP["?"])
        | '*' -> ^(GROUP_OP["*"])
        | '+' -> ^(GROUP_OP["+"])
        | range_op -> ^(range_op)
        ;
        
range_op: '[' DIGITS (',' DIGITS)? ']' -> ^(GROUP_OP["range"] DIGITS+);
          

WS: (' ' | '\t' | '\n' | '\r')+ { $channel = HIDDEN;};
SINGLE_COMMENT: '//' ~('\r' | '\n')* {$channel = HIDDEN;};
COMMENT:   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;};

STRING : '"' (~('"' | '\\') | '\\' .)* '"';
DIGITS: '0'..'9'+;
SIMPLE: ~('(' | ')' | ' ' | ',' | '.' | '<' | '>' | '\t' | '\r' | '\n' | '{' | '}' | '[' | ']' | ':')+;

