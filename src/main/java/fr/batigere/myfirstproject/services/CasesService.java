package fr.batigere.myfirstproject.services;

import fr.batigere.myfirstproject.daos.CasesDao;
import fr.batigere.myfirstproject.rest.dtos.Case;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import java.util.List;

@ApplicationScoped
public class CasesService {

    @Inject
    private CasesDao casesDao;

    public List<Case> getAllCases() {
        return casesDao.getAllCases();
    }

    public Case getCaseById(@PathParam("id") String id) {
        return casesDao.getCaseById(id);
    }

    public Case createNewCase(Case pCase) {
        return casesDao.save(pCase);
    }

    public Case updateCase(Case pCase) {
        return casesDao.save(pCase);
    }

    public boolean deleteCase(String id) {
        Case toDelete = casesDao.getCaseById(id);
        if (toDelete != null) {
            return casesDao.delete(toDelete);
        } else {
            return false;
        }
    }
}
