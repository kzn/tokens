grammar JapeNG4;

@header {
  package name.kazennikov.annotations;
}


jape: phase | multiPhase;

// multi phase grammar
multiPhase: 'MultiPhase:' SIMPLE phasesDecl;
phasesDecl: 'Phases:' SIMPLE+;

// single phase grammar
phase: 'Phase:' SIMPLE input opts? singleRule+;
input: 'Input:' SIMPLE+;
opts: 'Options:' option (',' option)*;
option: SIMPLE '=' SIMPLE;

singleRule: 'Rule:' SIMPLE priority? matcher+ '-->' action+;
priority: 'Priority:' integer;

// LHS grammar
matcher: group modif?;
group: '(' groupAnd ('|' groupAnd)*')';
groupAnd: groupElem+;
groupElem: matcher | simpleMatcher;

simpleMatcher: '{' annotSpec (',' annotSpec)* '}';
annotSpec: '!' simpleAnnotSpec
         | simpleAnnotSpec
         ;

simpleAnnotSpec: SIMPLE
               ( '.' SIMPLE op value
               | '@' SIMPLE op value
               | SIMPLE simpleMatcher
               )?
               ;

attrName: SIMPLE
        | STRING
        ;

value: SIMPLE
     | STRING
     | integer
     | floatingPoint
     ;

op: '!='
  | '=='
  | '=~'
  | '==~'
  | '>'
  | '>='
  | '<'
  | '<='
  | SIMPLE
  ;




modif: (':' SIMPLE)
     | '?'
     | '*'
     | '+'
     | '[' DIGITS (',' to=DIGITS?)? ']'
     ;

// RHS grammar
action: labelings | javaCode;
javaCode: '{' ( '}'
        | SIMPLE | DIGITS | STRING | '(' | ')' | ',' | '.' | '<' | '>' | '[' | ']' | ':' | '=' | '!=' | '+' | '-' |'!' | '|' | javaCode)* '}'
        ;

labelings: labeling (',' labeling)*;
labeling: ':' SIMPLE '.' SIMPLE '=' '{' (attr (',' attr )*)?'}';

attr:  attrName '=' attrValue;
attrValue: value
         |':' SIMPLE '.' SIMPLE '.' SIMPLE
         |':' SIMPLE '.' SIMPLE '@' SIMPLE
         ;



WS: (' ' | '\t' | '\n' | '\r')+ -> skip;
SINGLE_COMMENT: '//' ~('\r' | '\n')* -> skip;
COMMENT:   '/*' (.)*? '*/' -> skip;

integer: ('-'|'+')? DIGITS;

floatingPoint: ('-'|'+')? DIGITS '.' DIGITS exponent? ('f' | 'F' | 'd' 'D')?
             | '.' DIGITS exponent? ('f' | 'F' | 'd' 'D')?
             | DIGITS exponent  ('f' | 'F' | 'd' 'D')?
             | DIGITS exponent? ('f' | 'F' | 'd' 'D');

exponent: ('e' | 'E') ('-'|'+')? DIGITS;

STRING : '"' (~('"' | '\\') | '\\' .)* '"';
DIGITS: '0'..'9'+;
SIMPLE: ~('(' | ')' | ' ' | ',' | '.' | '<' | '>' | '\t' | '\r' | '\n' | '{' | '}' | '[' | ']' | ':' | '=' | '!' | '~' | '"' | '@')+;