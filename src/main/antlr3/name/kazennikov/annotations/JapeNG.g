grammar JapeNG;

options {
  language = Java;
  output = AST;
  ASTLabelType = CommonTree;
  backtrack = true;
  memoize = true;
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
    SIMPLE_RHS;
    ATTR;
    VAL;
    REF_VAL;
    NAME;
    EMPTY_RHS;
    JAVA;
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

rule: 'Rule:' name=SIMPLE priority? matcher+ '-->' action+ -> ^(RULE ^(NAME $name) priority? matcher+ action+);
priority: 'Priority:' num=integer -> ^(PRIORITY $num);
// LHS grammar
matcher: group modif? -> ^(GROUP_MATCHER modif? group);
group: '(' groupElem+ ('|' groupElem+)*')' -> ^(OR groupElem+);
groupElem: matcher | simpleMatcher;

simpleMatcher: '{' annotSpec (',' annotSpec)* '}' -> ^(ANNOT annotSpec+);
annotSpec: '!' simpleAnnotSpec -> ^(SIMPLE["NOT"] simpleAnnotSpec)
         | simpleAnnotSpec -> simpleAnnotSpec;
simpleAnnotSpec: type=SIMPLE ( -> ^(SIMPLE["AN_TYPE"] SIMPLE)
                               | '.' SIMPLE op value -> ^(SIMPLE["AN_FEAT"] op value SIMPLE+)
                               | '@' SIMPLE op value -> ^(SIMPLE["AN_METAFEAT"] op value SIMPLE+));
attrName: SIMPLE | STRING;
value: SIMPLE | STRING | integer | floatingPoint;
op: '!=' -> ^(OP["neq"])
  | '==' -> ^(OP["eq"]);




modif: (':' SIMPLE) -> ^(GROUP_OP SIMPLE["named"] SIMPLE)
        | '?' -> ^(GROUP_OP SIMPLE["?"])
        | '*' -> ^(GROUP_OP SIMPLE["*"])
        | '+' -> ^(GROUP_OP SIMPLE["+"])
        | '[' from=DIGITS ( -> ^(GROUP_OP SIMPLE["range"] DIGITS DIGITS)
                     | (',' to=DIGITS?) -> ^(GROUP_OP SIMPLE["range"] $from $to?)
                     ) ']' 
        ;
// RHS grammar
action: labelings | javaCode;
javaCode: '{' ( '}'-> ^(EMPTY_RHS)
                | code+=(SIMPLE | DIGITS | STRING | '(' | ')' | ',' | '.' | '<' | '>' | '[' | ']' | ':' | '=' | '!=' | '+' | '!' | javaCode)+ '}'
                -> ^(JAVA));
labelings: labeling (',' labeling)* -> labeling+;
labeling: ':' SIMPLE '.' SIMPLE '=' '{' attr (',' attr )*'}' -> ^(SIMPLE_RHS ^(NAME SIMPLE+) attr+);

attr:  attrName '=' attrValue -> ^(ATTR attrName attrValue);
attrValue: value -> ^(VAL value)
         |':' SIMPLE '.' SIMPLE '.' SIMPLE -> ^(REF_VAL SIMPLE+);
       
        
          

WS: (' ' | '\t' | '\n' | '\r')+ { $channel = HIDDEN;};
SINGLE_COMMENT: '//' ~('\r' | '\n')* {$channel = HIDDEN;};
COMMENT:   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;};

integer: ('-'|'+')? DIGITS;

floatingPoint: ('-'|'+')? DIGITS '.' DIGITS exponent? ('f' | 'F' | 'd' 'D')?
             | '.' DIGITS exponent? ('f' | 'F' | 'd' 'D')?
             | DIGITS exponent  ('f' | 'F' | 'd' 'D')?
             | DIGITS exponent? ('f' | 'F' | 'd' 'D');
             
exponent: ('e' | 'E') ('-'|'+')? DIGITS;

STRING : '"' (~('"' | '\\') | '\\' .)* '"';
DIGITS: '0'..'9'+;
SIMPLE: ~('(' | ')' | ' ' | ',' | '.' | '<' | '>' | '\t' | '\r' | '\n' | '{' | '}' | '[' | ']' | ':' | '=' | '!' | '~')+;

