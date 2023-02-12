package estg.ipp.pt.Utils;

import estg.ipp.pt.*;
import estg.ipp.pt.DataStructures.Graphs.Caminho;
import estg.ipp.pt.Enums.TipoLocal;
import estg.ipp.pt.Exceptions.IllegalArgumentException;
import estg.ipp.pt.Interfaces.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

public class Json {

    public static boolean exportarEmpresa(IGestaoEmpresas empresa, String path) {
        return Files.writeFile(path, exportarEmpresa(empresa).toString(4));
    }

    private static JSONObject exportarEmpresa(IGestaoEmpresas gestaoEmpresas) {
        JSONObject json = new JSONObject();
        JSONArray vendedoresJsonArray = new JSONArray();
        JSONArray locaisJsonArray = new JSONArray();
        JSONArray caminhosJsonArray = new JSONArray();

        for (Iterator<IVendedor> it = gestaoEmpresas.getVendedores(); it.hasNext(); ) {
            IVendedor vendedor = it.next();
            vendedoresJsonArray.put(exportarVendedor(vendedor));
        }

        locaisJsonArray.put(localToJsonObject(gestaoEmpresas.getEmpresa()));

        for (Iterator<ILocal> it = gestaoEmpresas.getMercados(); it.hasNext(); ) {
            IMercado mercado = (IMercado) it.next();
            locaisJsonArray.put(exportarMercado(mercado));
        }

        for (Iterator<ILocal> it = gestaoEmpresas.getArmazens(); it.hasNext(); ) {
            IArmazem armazem = (IArmazem) it.next();
            locaisJsonArray.put(exportarAramazem(armazem));
        }

        for (Iterator<Caminho<ILocal>> it = gestaoEmpresas.getCaminhos(); it.hasNext(); ) {
            Caminho<ILocal> caminho = it.next();
            caminhosJsonArray.put(exportarCaminho(caminho));
        }

        json.putOpt("vendedores", vendedoresJsonArray);
        json.putOpt("locais", locaisJsonArray);
        json.putOpt("caminhos", caminhosJsonArray);

        return json;
    }

    public static boolean exportarVendedor(IVendedor vendedor, String path) {
        return Files.writeFile(path, exportarVendedor(vendedor).toString(4));
    }

    private static JSONObject exportarVendedor(IVendedor vendedor) {
        JSONObject json = new JSONObject();
        json.putOpt("id", vendedor.getId());
        json.putOpt("nome", vendedor.getName());
        json.putOpt("capacidade", vendedor.getCapacidade());
        JSONArray mercadosJsonArray = new JSONArray();

        for (Iterator<ILocal> it = vendedor.getMercadosToVisit(); it.hasNext(); ) {
            IMercado mercado = (IMercado) it.next();
            mercadosJsonArray.put(mercado.getNome());
        }

        json.putOpt("mercados_a_visitar", mercadosJsonArray);
        return json;
    }

    public static boolean exportarMercado(IMercado mercado, String path) {
        return Files.writeFile(path, exportarMercado(mercado).toString(4));
    }

    private static JSONObject exportarMercado(IMercado mercado) {
        JSONObject json = localToJsonObject(mercado);
        JSONArray clientesJsonArray = new JSONArray();

        for (Iterator<Integer> it = mercado.getClientes(); it.hasNext(); ) {
            Integer cliente = it.next();
            clientesJsonArray.put(cliente);
        }

        json.putOpt("clientes", clientesJsonArray);
        return json;
    }

    public static boolean exportarAramazem(IArmazem armazem, String path) {
        return Files.writeFile(path, exportarAramazem(armazem).toString(4));
    }

    private static JSONObject exportarAramazem(IArmazem armazem) {
        JSONObject json = localToJsonObject(armazem);
        json.putOpt("capacidade", armazem.getCapacidade());
        json.putOpt("stock", armazem.getStock());
        return json;
    }

    private static JSONObject localToJsonObject(ILocal local) {
        JSONObject json = new JSONObject();
        json.putOpt("nome", local.getNome());
        json.putOpt("tipo", local.getTipo().toString());
        return json;
    }

    private static JSONObject exportarCaminho(Caminho<ILocal> caminho){
        JSONObject json = new JSONObject();
       json.putOpt("de", caminho.getDe().getNome());
       json.putOpt("para", caminho.getPara().getNome());
        json.putOpt("distancia",  caminho.getDistancia());
        return json;
    }

    public static IGestaoEmpresas importData(String path) throws IOException {
        IGestaoEmpresas empresa = null;

        JSONObject data;

        try {
            data = new JSONObject(Files.readFile(path));
        } catch (JSONException e) {
            throw new IOException("File not readable");
        }
        try {
            empresa = createLocais((JSONArray) data.get("locais"));

            createCaminhos(empresa, (JSONArray) data.get("caminhos"));

            createVendedores(empresa, (JSONArray) data.get("vendedores"));
        } catch (JSONException e) {
            throw new IOException("File not readable");
        }

        return empresa;
    }

    private static IGestaoEmpresas createLocais(JSONArray data) throws IOException {
        IGestaoEmpresas gestaoEmpresa = null;
        try {
            gestaoEmpresa = new GestaoEmpresa(createEmpresa(data));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            for (final Object datum : data) {
                final JSONObject resultObject = (JSONObject) datum;
                String nome = resultObject.getString("nome");
                String tipo = resultObject.getString("tipo");

                if (tipo.equals(TipoLocal.MERCADO.toString())) {
                    gestaoEmpresa.addMercado(createMercado(nome, (JSONArray) resultObject.get("clientes")));
                } else if (tipo.equals(TipoLocal.ARMAZEM.toString())) {
                    gestaoEmpresa.addArmazem(createArmazem(nome, resultObject));
                }
            }
        } catch (JSONException | NullPointerException e) {
            throw new IOException("Error read locais");
        }

        return gestaoEmpresa;
    }

    private static IEmpresa createEmpresa(JSONArray data) throws IOException {
        IEmpresa empresa = null;
        try {
            for (final Object datum : data) {
                final JSONObject resultObject = (JSONObject) datum;
                String nome = resultObject.getString("nome");
                String tipo = resultObject.getString("tipo");

                if (tipo.equals(TipoLocal.SEDE.toString())) {
                    empresa = new Empresa(nome);
                }
            }
        } catch (JSONException e) {
            throw new IOException("Error read locais");
        }

        return empresa;
    }

    private static IMercado createMercado(String name, JSONArray data) throws IOException {
        IMercado mercado = new Mercado(name);
        try {
            for (final Object datum : data) {
                mercado.addCliente(Utils.integerParseWithDefault(datum));
            }
        } catch (JSONException | NullPointerException e) {
            throw new IOException("Error read mercado");
        }
        return mercado;
    }

    private static IArmazem createArmazem(String name, JSONObject data) throws IOException {
        IArmazem local = null;
        try {
            Integer capacidade = data.getInt("capacidade");
            Integer stock = data.getInt("stock");

            local = new Armazem(name, capacidade, stock);
        } catch (JSONException e) {
            throw new IOException("Error read armazem");
        }
        return local;
    }

    private static void createCaminhos(IGestaoEmpresas empresa, JSONArray data) throws IOException {
        try {
            for (final Object datum : data) {
                final JSONObject resultObject = (JSONObject) datum;
                ILocal de = empresa.findLocal(resultObject.getString("de"));
                ILocal para = empresa.findLocal(resultObject.getString("para"));
                Double distancia = resultObject.getDouble("distancia");

                empresa.addCaminho(de, para, distancia);
            }
        } catch (JSONException | NullPointerException e) {
            throw new IOException("Error read caminhos");
        }
    }

    private static void createVendedores(IGestaoEmpresas gestaoEmpresa, JSONArray data) throws IOException {
        try {
            for (final Object datum : data) {
                final JSONObject resultObject = (JSONObject) datum;

                Integer id = resultObject.getInt("id");
                String nome = resultObject.getString("nome");
                Integer capacidade = resultObject.getInt("capacidade");

                IVendedor vendedor = new Vendedor(nome, capacidade);

                createMercadosVisitar(gestaoEmpresa, vendedor, (JSONArray) resultObject.get("mercados_a_visitar"));

                gestaoEmpresa.addVendedor(vendedor);
            }
        } catch (JSONException e) {
            throw new IOException("Error read vendedores");
        }
    }

    private static void createMercadosVisitar(IGestaoEmpresas gestaoEmpresa, IVendedor vendedor, JSONArray data) throws IOException {
        try {
            for (final Object datum : data) {
                IMercado mercado = (IMercado) gestaoEmpresa.findLocal(String.valueOf(datum), TipoLocal.MERCADO);
                System.out.println(String.valueOf(datum));
                vendedor.addMercadoToVisit(mercado);
            }
        } catch (JSONException e) {
            throw new IOException("Error read mercados_a_visitar");
        }
    }

}
