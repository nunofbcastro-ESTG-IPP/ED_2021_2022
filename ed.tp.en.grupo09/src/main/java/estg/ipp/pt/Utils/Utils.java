package estg.ipp.pt.Utils;

public final class Utils {
    /**
     * Construtor utilizado para privar a instanciação desta classe
     */
    private Utils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Retorna um valor decimal e arredonda-o a 2 casas decimais
     *
     * @param value Valor a arrendondar
     * @return Valor decimal com 2 casas decimais
     */
    public static double TwoCasesDecimals(final double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    /**
     * Converte determinado objeto para um objeto do tipo Integer
     *
     * @param number Objeto a converter
     * @return Objeto convertido para Integer
     */
    public static Integer integerParseWithDefault(final Object number) {
        try {
            return Integer.parseInt(number.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Converte determinado objeto para um objeto do tipo Double
     *
     * @param number Objeto a converter
     * @return Objeto convertido para Double
     */
    public static Double doubleParseWithDefault(final Object number) {
        try {
            return Double.parseDouble(number.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }
}
