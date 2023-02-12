package estg.ipp.pt;

import estg.ipp.pt.Enums.TipoLocal;
import estg.ipp.pt.Interfaces.*;

import java.util.Iterator;

public class Empresa extends Local implements IEmpresa {
    public Empresa(String nome) {
        super(nome, TipoLocal.SEDE);
    }
}
