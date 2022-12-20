package expression.exceptions;

public class UnknownOperandFoundException extends ParserException {
    public UnknownOperandFoundException(int pos, String unknownOperand) {
        super(pos + ": Unknown operand found '" + unknownOperand + "'");
    }
}
