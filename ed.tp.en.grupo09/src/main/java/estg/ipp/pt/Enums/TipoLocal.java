package estg.ipp.pt.Enums;

public enum TipoLocal {
    SEDE("Sede"),
    MERCADO("Mercado"),
    ARMAZEM("Armazém"),;

    private final String label;

    TipoLocal(final String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }
}
