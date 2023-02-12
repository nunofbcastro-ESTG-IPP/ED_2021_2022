package estg.ipp.pt;

import estg.ipp.pt.Enums.TipoLocal;
import estg.ipp.pt.Exceptions.IllegalArgumentException;
import estg.ipp.pt.Interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestaoEmpresaTest {
    IVendedor vendedor1, vendedor2;
    IGestaoEmpresas gestaoEmpresas = null;
    IEmpresa empresa;
    ILocal mercado1, mercado2, armazem1;

    @BeforeEach
    void setUp() {
        empresa = new Empresa("Sede");
        mercado1 = new Mercado("Mercado1");
        mercado2 = new Mercado("Mercado2");
        armazem1 = new Armazem("Armazem1", 750, 200);
        vendedor1 = new Vendedor("Jose", 250);
        try {
            gestaoEmpresas = new GestaoEmpresa(empresa);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addVendedor() {
        boolean expectedResult, actualResult;
        //Teste 1
        expectedResult = true;
        actualResult = gestaoEmpresas.addVendedor(vendedor1);;

        assertEquals(expectedResult, actualResult, "Test1: Expected result should be exception" + expectedResult);

        //Teste 2
        expectedResult = false;
        actualResult = gestaoEmpresas.addVendedor(vendedor1);;

        assertEquals(expectedResult, actualResult, "Test2: Expected result should be exception" + expectedResult);

        //Teste 3
        expectedResult = false;
        actualResult = gestaoEmpresas.addVendedor(null);

        assertEquals(expectedResult, actualResult, "Test3: Expected result should be exception" + expectedResult);

    }

    @Test
    void findLocal() {
        //Teste 1
        ILocal expectedResult = null;
        ILocal actualResult = gestaoEmpresas.findLocal(mercado1.getNome());

        assertEquals(expectedResult, actualResult, "Test1: Expected result should be exception" + expectedResult);

        //Teste 2
        gestaoEmpresas.addMercado((IMercado) mercado1);
        expectedResult = mercado1;
        actualResult = gestaoEmpresas.findLocal(mercado1.getNome());

        assertEquals(expectedResult, actualResult, "Test2: Expected result should be exception" + expectedResult);
    }

    @Test
    void testFindLocal() {
        //Teste 1
        ILocal expectedResult = null;
        ILocal actualResult = gestaoEmpresas.findLocal(mercado1.getNome(), TipoLocal.ARMAZEM);

        assertEquals(expectedResult, actualResult, "Test1: Expected result should be exception" + expectedResult);

        //Teste 2
        gestaoEmpresas.addArmazem((IArmazem) armazem1);
        expectedResult = armazem1;
        actualResult = gestaoEmpresas.findLocal(armazem1.getNome(), TipoLocal.ARMAZEM);

        assertEquals(expectedResult, actualResult, "Test2: Expected result should be exception" + expectedResult);
    }
}