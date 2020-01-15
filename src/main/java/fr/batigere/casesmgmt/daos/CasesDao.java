package fr.batigere.casesmgmt.daos;

import fr.batigere.casesmgmt.entities.CaseEntity;

import java.util.List;

public interface CasesDao {
    public List<CaseEntity> findAll();

    public CaseEntity retrieve(String id);

    public CaseEntity save(CaseEntity c);

    public boolean delete(String id);
}
