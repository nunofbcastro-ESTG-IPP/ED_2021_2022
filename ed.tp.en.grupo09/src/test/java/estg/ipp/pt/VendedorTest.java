package estg.ipp.pt;

import estg.ipp.pt.Exceptions.IllegalArgumentException;
import estg.ipp.pt.Interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendedorTest {
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
        vendedor2 = new Vendedor("Antonio", 400);

        try {
            gestaoEmpresas = new GestaoEmpresa(empresa);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addMercadoToVisit() {
        boolean expectedResult, actualResult;
        //Teste 1
        expectedResult = true;
        actualResult = vendedor1.addMercadoToVisit((IMercado) mercado1);

        assertEquals(expectedResult, actualResult, "Test1: Expected result should be exception" + expectedResult);

        //Teste 2
        expectedResult = false;
        actualResult = vendedor1.addMercadoToVisit((IMercado) mercado1);

        assertEquals(expectedResult, actualResult, "Test2: Expected result should be exception" + expectedResult);

        //Teste 3
        expectedResult = false;
        actualResult = vendedor1.addMercadoToVisit(null);

        assertEquals(expectedResult, actualResult, "Test3: Expected result should be exception" + expectedResult);
    }

    @Test
    void removeMercado() {
        boolean expectedResult, actualResult;
        //Teste 1
        vendedor1.addMercadoToVisit((IMercado) mercado1);
        expectedResult = true;
        actualResult = vendedor1.removeMercado((IMercado) mercado1);

        assertEquals(expectedResult, actualResult, "Test1: Expected result should be exception" + expectedResult);

        //Teste 2
        expectedResult = false;
        actualResult = vendedor2.removeMercado((IMercado) mercado1);

        assertEquals(expectedResult, actualResult, "Test2: Expected result should be exception" + expectedResult);

        //Teste 3
        expectedResult = false;
        actualResult = vendedor1.removeMercado(null);

        assertEquals(expectedResult, actualResult, "Test3: Expected result should be exception" + expectedResult);
    }
}