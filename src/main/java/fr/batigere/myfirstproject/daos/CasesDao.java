package fr.batigere.myfirstproject.daos;

import fr.batigere.myfirstproject.rest.dtos.Case;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ApplicationScoped
public class CasesDao {
    private List<Case> fakeDb = new ArrayList<>();

    public List<Case> getAllCases() {
        return fakeDb;
    }

    public Case getCaseById(String id) {
        Case res = null;
        try {
            res = fakeDb.stream()
                    .filter(c -> id.equals(c.getId()))
                    .findFirst().get();
        } catch (NoSuchElementException e) {
            res = null;
        }
        return res;
    }

    public Case save(Case c) {
        Case existingCase = this.getCaseById(c.getId());
        if (existingCase != null) {
            this.delete(existingCase);
        }

        fakeDb.add(c);
        return c;
    }

    public boolean delete(Case c) {
        boolean res = false;
        if (this.getCaseById(c.getId()) != null) {
            fakeDb.removeIf(cs -> c.getId().equals(cs.getId()));
            res = true;
        }

        return res;
    }
}
