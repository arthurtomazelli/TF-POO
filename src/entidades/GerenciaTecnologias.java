package entidades;

import java.util.ArrayList;
import java.util.List;

public class GerenciaTecnologias {
    private List<Tecnologia> tecnologias;

    public GerenciaTecnologias() {
        tecnologias = new ArrayList<>();
    }

    public boolean addTecnologia(Tecnologia tecnologia) {
        if(idRepetido(tecnologia)) {
            return false;
        }

        tecnologias.add(tecnologia);

        return true;
    }

    public boolean idRepetido(Tecnologia novaTecnologia) {
        for (Tecnologia t : tecnologias) {
            if (t.getId() == novaTecnologia.getId()) {
                return true;
            }
        }

        return false;
    }

    public List<Tecnologia> getTecnologias() {
        return tecnologias;
    }
}
