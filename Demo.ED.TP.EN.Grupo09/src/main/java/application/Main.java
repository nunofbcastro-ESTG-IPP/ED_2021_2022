package application;

import estg.ipp.pt.*;
import estg.ipp.pt.DataStructures.ArrayList.ArrayUnorderedList;
import estg.ipp.pt.DataStructures.Graphs.Caminho;
import estg.ipp.pt.DataStructures.Interfaces.UnorderedListADT;
import estg.ipp.pt.Exceptions.IllegalArgumentException;
import estg.ipp.pt.Interfaces.*;
import estg.ipp.pt.Utils.Json;

import java.io.IOException;
import java.util.Iterator;

public class Main {

    private final static String NEXTITEM = "----------------------------------------";

    private static void adicionarVendedor(IGestaoEmpresas gestaoEmpresas) {
        String nome = Inputs.readString("Nome do vendedor:");
        int capacidade = Inputs.readInt("Capacidade do vendedor:", 1, Integer.MAX_VALUE);
        IVendedor vendedor = new Vendedor(nome, capacidade);
        gestaoEmpresas.addVendedor(vendedor);
    }

    private static void adicionarMercado(IGestaoEmpresas gestaoEmpresas) {
        IMercado mercado = new Mercado(Inputs.readString("Nome do mercado:"));
        gestaoEmpresas.addMercado(mercado);
    }

    private static void adicionarArmazem(IGestaoEmpresas gestaoEmpresas) {
        String nome = Inputs.readString("Nome do armazém:");
        int capacidade = Inputs.readInt("Capacidade do armazém:", 1, Integer.MAX_VALUE);
        int stock = Inputs.readInt("Stock do armazém:", 0, capacidade);
        IArmazem armazem = new Armazem(nome, capacidade, stock);
        gestaoEmpresas.addArmazem(armazem);
    }

    private static void adicionarCaminho(IGestaoEmpresas gestaoEmpresas){
        Iterator<ILocal> armazens = gestaoEmpresas.getArmazens();
        Iterator<ILocal> mercados = gestaoEmpresas.getMercados();

        UnorderedListADT<ILocal>  locais = new ArrayUnorderedList();

        locais.addToRear(gestaoEmpresas.getEmpresa());
        while (armazens.hasNext()){
            locais.addToRear(armazens.next());
        }
        while (mercados.hasNext()){
            locais.addToRear(mercados.next());
        }
        int c=0;
        for (ILocal local: locais) {
            System.out.println(c+" - "+local.getNome());
        }
        if(c<2){
            System.out.println("Não tem pontos suficientes");
        }
        int option1 = Inputs.readInt("Selecione o local 1:", 1, c);
        int option2 = Inputs.readInt("Selecione o local 2:", 1, c);
        ILocal local1 = null;
        ILocal local2 = null;
        Iterator<ILocal> locaisI = locais.iterator();
        for (int i = 0; i < option1; i++) {
            local1 = locaisI.next();
        }
        locaisI = locais.iterator();
        for (int i = 0; i < option2; i++) {
            local2 = locaisI.next();
        }
        double size = Inputs.readDouble("Tamanho do caminho:",1,Double.MAX_VALUE);
        gestaoEmpresas.addCaminho(local1,local2,size);
    }

    private static void listarEmpresa(IGestaoEmpresas gestaoEmpresas) {
        System.out.println("Nome da empresa: " + gestaoEmpresas.getEmpresa().getNome());
        listarVendedores(gestaoEmpresas);
        listarArmazens(gestaoEmpresas);
        listarMercados(gestaoEmpresas);
        listarCaminhos(gestaoEmpresas);
    }

    private static void printVendedores(IVendedor vendedor) {
        System.out.println("Id: " + vendedor.getId());
        System.out.println("Nome: " + vendedor.getName());
        System.out.println("Capacidade: " + vendedor.getCapacidade());
        Iterator<ILocal> mercados = vendedor.getMercadosToVisit();
        if (mercados.hasNext()) {
            System.out.println("Mercados para visistar: ");
            System.out.println(mercados.next().getNome());
        }
    }

    private static void listarVendedores(IGestaoEmpresas gestaoEmpresas) {
        int i = 1;
        Iterator<IVendedor> vendedores = gestaoEmpresas.getVendedores();
        while (vendedores.hasNext()) {
            IVendedor vendedor = vendedores.next();
            System.out.println("Vendedor " + i);
            printVendedores(vendedor);
            i++;
            System.out.println(NEXTITEM);
        }
    }

    private static void printMercado(IMercado mercado) {
        System.out.println("Nome: " + mercado.getNome());
        Iterator<Integer> clientes = mercado.getClientes();
        if (clientes.hasNext()) {
            System.out.println("Clientes: ");
            do {
                System.out.println(clientes.next().toString());
            } while (clientes.hasNext());
        }
    }

    private static void listarMercados(IGestaoEmpresas gestaoEmpresas) {
        int i = 0;
        Iterator<ILocal> mercados = gestaoEmpresas.getMercados();
        while (mercados.hasNext()) {
            i++;
            IMercado mercado = (IMercado) mercados.next();
            System.out.println("Mercado " + i);
            printMercado(mercado);
            System.out.println(NEXTITEM);
        }
    }

    private static void printArmazem(IArmazem armazem) {
        System.out.println("Nome: " + armazem.getNome());
        System.out.println("Capacidade: " + armazem.getCapacidade());
        System.out.println("Stock: " + armazem.getStock());
    }

    private static void listarArmazens(IGestaoEmpresas gestaoEmpresas) {
        Iterator<ILocal> armazens = gestaoEmpresas.getArmazens();
        while (armazens.hasNext()) {
            IArmazem armazem = (IArmazem) armazens.next();
            printArmazem(armazem);
            System.out.println(NEXTITEM);
        }
    }

    private static void printCaminho(Caminho<ILocal> caminho) {
        System.out.println("De: " + caminho.getDe().getNome());
        System.out.println("Para: " + caminho.getPara().getNome());
        System.out.println("Distância: " + caminho.getDistancia());
    }

    private static void listarCaminhos(IGestaoEmpresas gestaoEmpresas) {
        Iterator<Caminho<ILocal>> caminhos = gestaoEmpresas.getCaminhos();
        while (caminhos.hasNext()) {
            Caminho<ILocal> caminho = caminhos.next();
            printCaminho(caminho);
            System.out.println(NEXTITEM);
        }
    }

    private static IMercado selecionarMercado(IGestaoEmpresas gestaoEmpresas, String mensagem) {
        Iterator<ILocal> mercadosListar = gestaoEmpresas.getMercados();
        Iterator<ILocal> mercadosPesquisar = gestaoEmpresas.getMercados();
        int c = 0;
        while (mercadosListar.hasNext()) {
            c++;
            System.out.println(c + " - " + mercadosListar.next().getNome());
        }
        if (c <= 0) {
            return null;
        }
        int option = Inputs.readInt(mensagem, 1, c);
        IMercado mercado = null;
        for (int i = 0; i < option; i++) {
            mercado = (IMercado) mercadosPesquisar.next();

        }
        return mercado;
    }

    private static IArmazem selecionarArmazem(IGestaoEmpresas gestaoEmpresas, String mensagem) {
        Iterator<ILocal> armazemListar = gestaoEmpresas.getArmazens();
        Iterator<ILocal> armazemPesquisar = gestaoEmpresas.getArmazens();
        int c = 0;
        while (armazemListar.hasNext()) {
            c++;
            System.out.println(c + " - " + armazemListar.next().getNome());
        }
        if (c <= 0) {
            return null;
        }
        int option = Inputs.readInt(mensagem, 1, c);
        IArmazem armazem = null;
        for (int i = 0; i < option; i++) {
            armazem = (IArmazem) armazemPesquisar.next();
        }
        return armazem;
    }

    private static IVendedor selecionarVendedor(IGestaoEmpresas gestaoEmpresas, String mensagem) {
        Iterator<IVendedor> vendedorListar = gestaoEmpresas.getVendedores();
        Iterator<IVendedor> vendedorPesquisar = gestaoEmpresas.getVendedores();
        int c = 0;
        while (vendedorListar.hasNext()) {
            c++;
            IVendedor vendedor = vendedorListar.next();
            System.out.println(c + " - ID: " + vendedor.getId() + " Nome: " + vendedor.getName());
        }
        if (c <= 0) {
            return null;
        }
        int option = Inputs.readInt(mensagem, 1, c);
        IVendedor vendedor = null;
        for (int i = 0; i < option; i++) {
            vendedor = vendedorPesquisar.next();
        }
        return vendedor;
    }

    private static void exportarArmazem(IGestaoEmpresas gestaoEmpresas) {
        IArmazem armazem = selecionarArmazem(gestaoEmpresas, "Selecione um armazém: ");
        if (armazem == null) {
            System.out.println("Não existem armazéns");
            return;
        }
        String path = Inputs.readString("Pasta/NomeFicheiro:");
        if (Json.exportarAramazem(armazem, path)) {
            System.out.println("Dados exportados com sucesso");
        } else {
            System.out.println("Error ao exportar dados");
        }
    }

    private static void exportarVendedor(IGestaoEmpresas gestaoEmpresas) {
        IVendedor vendedor = selecionarVendedor(gestaoEmpresas, "Selecione um vendedor: ");
        if (vendedor == null) {
            System.out.println("Não existem vendedores");
            return;
        }
        String path = Inputs.readString("Pasta/NomeFicheiro:");
        if (Json.exportarVendedor(vendedor, path)) {
            System.out.println("Dados exportados com sucesso");
        } else {
            System.out.println("Error ao exportar dados");
        }
    }

    private static void exportarMercado(IGestaoEmpresas gestaoEmpresas) {
        IMercado mercado = selecionarMercado(gestaoEmpresas, "Selecione um mercado: ");
        if (mercado == null) {
            System.out.println("Não existem mercados");
            return;
        }
        String path = Inputs.readString("Pasta/NomeFicheiro:");
        if (Json.exportarMercado(mercado, path)) {
            System.out.println("Dados exportados com sucesso");
        } else {
            System.out.println("Error ao exportar dados");
        }
    }

    private static void exportarEmpresa(IGestaoEmpresas gestaoEmpresas) {
        String path = Inputs.readString("Pasta/NomeFicheiro:");
        if (Json.exportarEmpresa(gestaoEmpresas, path)) {
            System.out.println("Dados exportados com sucesso");
        } else {
            System.out.println("Error ao exportar dados");
        }
    }

    private static void gerarRota(IGestaoEmpresas gestaoEmpresas) {
        IVendedor vendedor = selecionarVendedor(gestaoEmpresas, "Selecione um vendedor: ");
        if (vendedor == null) {
            System.out.println("Não existem vendedores");
            return;
        }
        gestaoEmpresas.startRota(vendedor);
    }

    private static void mudarNomeEmpresa(IGestaoEmpresas gestaoEmpresas){
        String nome = Inputs.readString("Nome do armazem:");
        gestaoEmpresas.getEmpresa().setNome(nome);
    }

    private static void menuEmpresa(IGestaoEmpresas gestaoEmpresas){
        int option;
        do {
            System.out.println("=================================");
            System.out.println("|          Menu Empresas         |");
            System.out.println("=================================");
            System.out.println("| Opções:                        |");
            System.out.println("|   1. Listar Empresa            |");
            System.out.println("|   2. Altear Nome               |");
            System.out.println("|   2. Exportar Empresa          |");
            System.out.println("|   0. Voltar                    |");
            System.out.println("=================================");
            option = Inputs.readInt("Insira uma opção:", 0, 3);
            switch (option) {
                case 0:
                    break;
                case 1:
                    listarEmpresa(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 2:
                    mudarNomeEmpresa(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 3:
                    exportarEmpresa(gestaoEmpresas);
                    Inputs.pause();
                    break;
            }
        } while (option != 0);
    }

    private static void menuCaminhos(IGestaoEmpresas gestaoEmpresas){
        int option;
        do {
            System.out.println("=================================");
            System.out.println("|          Menu Caminhos         |");
            System.out.println("=================================");
            System.out.println("| Opções:                        |");
            System.out.println("|   1. Listar Caminhos           |");
            System.out.println("|   2. Adicionar/Alterar caminho |");
            System.out.println("|   0. Voltar                    |");
            System.out.println("=================================");
            option = Inputs.readInt("Insira uma opção:", 0, 2);
            switch (option) {
                case 0:
                    break;
                case 1:
                    listarCaminhos(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 2:
                    adicionarCaminho(gestaoEmpresas);
                    Inputs.pause();
                    break;
            }
        } while (option != 0);
    }

    private static void alterarNomeArmazem(IGestaoEmpresas gestaoEmpresas){
        IArmazem armazem = selecionarArmazem(gestaoEmpresas, "Selecione um armazem: ");
        if (armazem == null) {
            System.out.println("Não existem armazens");
            return;
        }
        String nome = Inputs.readString("Nome do armazem:");
        armazem.setNome(nome);
    }

    private static void alterarCapacidadeArmazem(IGestaoEmpresas gestaoEmpresas){
        IArmazem armazem = selecionarArmazem(gestaoEmpresas, "Selecione um armazem: ");
        if (armazem == null) {
            System.out.println("Não existem armazens");
            return;
        }
        int capacidade = Inputs.readInt("Capacidade do armazem:",1, Integer.MAX_VALUE);
        armazem.setCapacidade(capacidade);

    }

    private static void adicionarStockArmazem(IGestaoEmpresas gestaoEmpresas){
        IArmazem armazem = selecionarArmazem(gestaoEmpresas, "Selecione um armazem: ");
        if (armazem == null) {
            System.out.println("Não existem armazens");
            return;
        }
        int stock = Inputs.readInt("Capacidade do armazem:",0, armazem.getCapacidade()-armazem.getStock());
        armazem.setStock(stock);

    }

    private static void menuArmazens(IGestaoEmpresas gestaoEmpresas){
        int option;
        do {
            System.out.println("==============================");
            System.out.println("|        Menu Armazens       |");
            System.out.println("==============================");
            System.out.println("| Opções:                    |");
            System.out.println("|   1. Listar Armazens       |");
            System.out.println("|   2. Adicionar Armazem     |");
            System.out.println("|   3. Adicionar Stock       |");
            System.out.println("|   4. Alterar Nome          |");
            System.out.println("|   5. Alterar Capacidade    |");
            System.out.println("|   6. Exportar Armazem      |");
            System.out.println("|   0. Voltar                |");
            System.out.println("==============================");
            option = Inputs.readInt("Insira uma opção:", 0, 6);
            switch (option) {
                case 0:
                    break;
                case 1:
                    listarArmazens(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 2:
                    adicionarArmazem(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 3:
                    adicionarStockArmazem(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 4:
                    alterarNomeArmazem(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 5:
                    alterarCapacidadeArmazem(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 6:
                    exportarArmazem(gestaoEmpresas);
                    Inputs.pause();
                    break;
            }
        } while (option != 0);
    }

    private static void adicionarClientes(IGestaoEmpresas gestaoEmpresas){
        IMercado mercado = selecionarMercado(gestaoEmpresas, "Selecione um mercado: ");
        if (mercado == null) {
            System.out.println("Não existem vendedores");
            return;
        }
        int cliente = Inputs.readInt("Contidade cliente:",1,150);
        mercado.addCliente(cliente);
    }

    private static void alterarNomeMercado(IGestaoEmpresas gestaoEmpresas){
        IMercado mercado = selecionarMercado(gestaoEmpresas, "Selecione um mercado: ");
        if (mercado == null) {
            System.out.println("Não existem vendedores");
            return;
        }
        String nome = Inputs.readString("Nome do mercado:");
        mercado.setNome(nome);
    }

    private static void menuMercado(IGestaoEmpresas gestaoEmpresas){
        int option;
        do {
            System.out.println("==============================");
            System.out.println("|        Menu Mercados       |");
            System.out.println("==============================");
            System.out.println("| Opções:                    |");
            System.out.println("|   1. Listar Mercados       |");
            System.out.println("|   2. Adicionar Mercado     |");
            System.out.println("|   3. Adicionar clientes    |");
            System.out.println("|   4. Alterar Nome          |");
            System.out.println("|   5. Exportar Mercados     |");
            System.out.println("|   0. Voltar                |");
            System.out.println("==============================");
            option = Inputs.readInt("Insira uma opção:", 0, 5);
            switch (option) {
                case 0:
                    break;
                case 1:
                    listarMercados(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 2:
                    adicionarMercado(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 3:
                    adicionarClientes(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 4:
                    alterarNomeMercado(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 5:
                    exportarMercado(gestaoEmpresas);
                    Inputs.pause();
                    break;
            }
        } while (option != 0);
    }

    private static void alterarNomeVendedor(IGestaoEmpresas gestaoEmpresas) {
        IVendedor vendedor = selecionarVendedor(gestaoEmpresas, "Selecione um vendedor: ");
        if (vendedor == null) {
            System.out.println("Não existem vendedores");
            return;
        }
        String nome = Inputs.readString("Nome do vendedor:");
        vendedor.setName(nome);
    }

    private static void alterarCapacidade(IGestaoEmpresas gestaoEmpresas) {
        IVendedor vendedor = selecionarVendedor(gestaoEmpresas, "Selecione um vendedor: ");
        if (vendedor == null) {
            System.out.println("Não existem vendedores");
            return;
        }
        int capacidade = Inputs.readInt("Capacidade do vendedor:",1, Integer.MAX_VALUE);
        vendedor.setCapacidade(capacidade);
    }

    private static void adicionarMercadoVisistar(IGestaoEmpresas gestaoEmpresas){
        IVendedor vendedor = selecionarVendedor(gestaoEmpresas, "Selecione um vendedor: ");
        if (vendedor == null) {
            System.out.println("Não existem vendedores");
            return;
        }
        IMercado mercado = selecionarMercado(gestaoEmpresas, "Selecione um mercado a visitar: ");
        if (mercado == null) {
            System.out.println("Não existem mercado");
            return;
        }
        vendedor.addMercadoToVisit(mercado);
    }

    private static void menuVendedores(IGestaoEmpresas gestaoEmpresas){
        int option;
        do {
            System.out.println("====================================");
            System.out.println("|          Menu Vendedores          |");
            System.out.println("=====================================");
            System.out.println("| Opções:                           |");
            System.out.println("|   1. Listar Vendedores            |");
            System.out.println("|   2. Adicionar Vendedor           |");
            System.out.println("|   3. Alterar Capacidade           |");
            System.out.println("|   4. Alterar Nome                 |");
            System.out.println("|   5. Adicionar mercado a visitar  |");
            System.out.println("|   6. Gerar Rota                   |");
            System.out.println("|   7. Exportar Vendedores          |");
            System.out.println("|   0. Voltar                       |");
            System.out.println("=====================================");
            option = Inputs.readInt("Insira uma opção:", 0, 7);
            switch (option) {
                case 0:
                    break;
                case 1:
                    listarVendedores(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 2:
                    adicionarVendedor(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 3:
                    alterarCapacidade(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 4:
                    alterarNomeVendedor(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 5:
                    adicionarMercadoVisistar(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 6:
                    gerarRota(gestaoEmpresas);
                    Inputs.pause();
                    break;
                case 7:
                    exportarVendedor(gestaoEmpresas);
                    Inputs.pause();
                    break;
            }
        } while (option != 0);
    }
    
    private static void menu(IGestaoEmpresas gestaoEmpresas) {
        int option;
        do {
            System.out.println("==============================");
            System.out.println("|            MENU            |");
            System.out.println("==============================");
            System.out.println("| Opções:                    |");
            System.out.println("|   1. Menu Vendedores       |");
            System.out.println("|   2. Menu Armazens         |");
            System.out.println("|   3. Menu Mercados         |");
            System.out.println("|   4. Menu Caminhos         |");
            System.out.println("|   5. Menu Empresa          |");
            System.out.println("|   0. Sair                  |");
            System.out.println("==============================");
            option = Inputs.readInt("Insira uma opção:", 0, 5);
            switch (option) {
                case 0:
                    break;
                case 1:
                    menuVendedores(gestaoEmpresas);
                    break;
                case 2:
                    menuArmazens(gestaoEmpresas);
                    break;
                case 3:
                    menuMercado(gestaoEmpresas);
                    break;
                case 4:
                    menuCaminhos(gestaoEmpresas);
                    break;
                case 5:
                    menuEmpresa(gestaoEmpresas);
                    break;
            }
        } while (option != 0);
    }

    private static IGestaoEmpresas importarEmpresa() {
        IGestaoEmpresas gestaoEmpresas = null;
        String path = Inputs.readString("Pasta/NomeFicheiro:");
        try {
            gestaoEmpresas = Json.importData(path);
            System.out.println("Dados importados com sucesso");
        } catch (IOException e) {
            System.out.println("Error ao importar dados");
        }
        return gestaoEmpresas;
    }

    private static IGestaoEmpresas criarEmpresa() {
        String nomeEmpresa = Inputs.readString("Nome Empresa:");
        IGestaoEmpresas gestaoEmpresas = null;
        try {
            gestaoEmpresas = new GestaoEmpresa(new Empresa(nomeEmpresa));
        } catch (IllegalArgumentException e) {
            System.out.println("Nome invalido");
        }
        return gestaoEmpresas;
    }

    private static IGestaoEmpresas createEmpresa() {
        IGestaoEmpresas gestaoEmpresas = null;

        int option;
        do {
            System.out.println("=========================");
            System.out.println("|         MENU          |");
            System.out.println("=========================");
            System.out.println("| Opções:               |");
            System.out.println("|   1. Importar dados   |");
            System.out.println("|   2. Criar empresa    |");
            System.out.println("|   0. Sair             |");
            System.out.println("=========================");
            option = Inputs.readInt("Insira uma opção:", 0, 2);
            switch (option) {
                case 0:
                    break;
                case 1:
                    gestaoEmpresas = importarEmpresa();
                    Inputs.pause();
                    break;
                case 2:
                    gestaoEmpresas = criarEmpresa();
                    Inputs.pause();
                    break;
            }
        } while (option != 0 && gestaoEmpresas == null);

        return gestaoEmpresas;
    }

    public static void main(String[] args) {
        IGestaoEmpresas gestaoEmpresas = createEmpresa();
        if (gestaoEmpresas != null) {
            menu(gestaoEmpresas);
        }
        System.out.println("Obrigado, volte sempre.");
    }

}
