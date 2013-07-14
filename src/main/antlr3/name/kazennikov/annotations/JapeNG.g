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
    RULE;
    NAME;
    PRIORITY;
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
option: SIMPLE '=' SIMPLE -> ^(SIMPLE SIMPLE);

rule: 'Rule:' name=SIMPLE priority? matcher+ '-->' actions -> ^(RULE ^(NAME $name) priority? matcher+);
priority: 'Priority:' num=integer -> ^(PRIORITY $num);
integer: ('-'|'+')? DIGITS;
matcher: group modif? -> ^(GROUP_MATCHER modif? group);
group: '(' group_elem+ ('|' group_elem+)*')' -> ^(OR group_elem+);

simple_matcher: '{' annot_spec (',' annot_spec)* '}' -> ^(ANNOT annot_spec+);

annot_spec: '!' simple_annot_spec -> ^(SIMPLE["NOT"] simple_annot_spec)
          | simple_annot_spec -> simple_annot_spec;
          
simple_annot_spec: type=SIMPLE (-> ^(SIMPLE["AN_TYPE"] SIMPLE)
                               | '.' SIMPLE op val -> ^(SIMPLE["AN_FEAT"] op val SIMPLE+)
                               | '@' SIMPLE op val -> ^(SIMPLE["AN_METAFEAT"] op val SIMPLE+));
val: SIMPLE | STRING | DIGITS;
op: '!=' -> ^(OP["neq"])
  | '==' -> ^(OP["eq"]);


group_elem: matcher | simple_matcher;

modif: (':' SIMPLE) -> ^(GROUP_OP SIMPLE["named"] SIMPLE)
        | '?' -> ^(GROUP_OP SIMPLE["?"])
        | '*' -> ^(GROUP_OP SIMPLE["*"])
        | '+' -> ^(GROUP_OP SIMPLE["+"])
        | '[' from=DIGITS ( -> ^(GROUP_OP SIMPLE["range"] DIGITS DIGITS)
                     | (',' to=DIGITS?) -> ^(GROUP_OP SIMPLE["range"] $from $to?)
                     ) ']' 
        ;
actions: (labelings|java_code)+;
java_code: '{' (SIMPLE | DIGITS | STRING | '(' | ')' | ',' | '.' | '<' | '>' | '[' | ']' | ':' | '=' | '!=' | '+' | '!' | java_code)* '}';
labelings: labeling (',' labeling)* -> labeling+;
labeling: ':' SIMPLE '.' SIMPLE '=' '{' attrvalue (',' attrvalue )*'}';
attrvalue: (SIMPLE | STRING) '=' attrval;
attrval: SIMPLE | STRING | ':' SIMPLE '.' SIMPLE '.' SIMPLE;
        
          

WS: (' ' | '\t' | '\n' | '\r')+ { $channel = HIDDEN;};
SINGLE_COMMENT: '//' ~('\r' | '\n')* {$channel = HIDDEN;};
COMMENT:   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;};

STRING : '"' (~('"' | '\\') | '\\' .)* '"';
DIGITS: '0'..'9'+;
SIMPLE: ~('(' | ')' | ' ' | ',' | '.' | '<' | '>' | '\t' | '\r' | '\n' | '{' | '}' | '[' | ']' | ':' | '=' | '!' | '~')+;

