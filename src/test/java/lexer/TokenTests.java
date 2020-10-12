package lexer;

import common.PeekIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenTests {

    void assertToken(Token token, String value, TokenType type) {
        assertEquals(value, token.getValue());
        assertEquals(type, token.getType());
    }

    @Test
    public void  test_varOrKeyword() {
        PeekIterator<Character> it1 = new PeekIterator<Character>("if abc".chars().mapToObj(x -> (char) x));
        PeekIterator<Character> it2 = new PeekIterator<Character>("true abc".chars().mapToObj(x -> (char) x));
        Token token1 = Token.makeVarOrKeyword(it1);
        Token token2 = Token.makeVarOrKeyword(it2);

        assertToken(token1, "if", TokenType.KEYWORD);
        assertToken(token2, "true", TokenType.BOOLEAN);
        it1.next();
        Token token3 = Token.makeVarOrKeyword(it1);

        assertToken(token3, "abc", TokenType.VARIABLE);

    }

    @Test
    public void test_makeString() throws LexicalException {
        String[] tests = {
                "\"123\"",
                "\'123\'"
        };

        for(String test:tests) {
            PeekIterator<Character>  it = new PeekIterator<Character>(test.chars().mapToObj(x ->(char)x));
            Token token = Token.makeString(it);
            assertToken(token, test, TokenType.STRING);
        }

    }


}
