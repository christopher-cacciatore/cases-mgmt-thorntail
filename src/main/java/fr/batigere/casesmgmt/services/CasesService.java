package fr.batigere.casesmgmt.services;

import fr.batigere.casesmgmt.daos.CasesDao;
import fr.batigere.casesmgmt.daos.UsersDao;
import fr.batigere.casesmgmt.entities.CaseEntity;
import fr.batigere.casesmgmt.entities.UserEntity;
import fr.batigere.casesmgmt.exceptions.UserNotFoundException;
import fr.batigere.casesmgmt.rest.dtos.Case;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class CasesService {

    @Inject
    private CasesDao casesDao;

    @Inject
    private UsersDao usersDao;

    @Traced
    public List<Case> getAllCases() {
        List<CaseEntity> entities = casesDao.findAll();
        return entities.stream().map(c -> this.entityToDto(c)).collect(Collectors.toList());
    }

    @Traced
    public Case getCaseById(String id) {
        CaseEntity entity = casesDao.retrieve(id);
        return this.entityToDto(entity);
    }

    @Traced
    public Case createNewCase(Case pCase) throws UserNotFoundException {
        CaseEntity entity = this.dtoToEntity(pCase);
        UserEntity user = null;
        try {
            user = usersDao.findByUsername(pCase.getCreatedBy());
        } catch (NoResultException e){
            throw new UserNotFoundException(e);
        }
        entity.setCreatedBy(user);
        CaseEntity createdEntity = casesDao.save(entity);
        return this.entityToDto(createdEntity);
    }

    @Traced
    public Case updateCase(Case pCase) throws UserNotFoundException {
        if(casesDao.retrieve(pCase.getId()) == null){
            return null;
        }

        CaseEntity entity = this.dtoToEntity(pCase);
        UserEntity user = null;
        try {
            user = usersDao.findByUsername(pCase.getCreatedBy());
        } catch (NoResultException e){
            throw new UserNotFoundException(e);
        }
        entity.setCreatedBy(user);
        CaseEntity updatedEntity = casesDao.save(entity);
        return this.entityToDto(updatedEntity);
    }

    @Traced
    public boolean deleteCase(String id) {
        return casesDao.delete(id);
    }

    private Case entityToDto(CaseEntity c){
        if(c== null){
            return null;
        }

        Case cDto = new Case();
        cDto.setId(c.getId());
        cDto.setTitle(c.getTitle());
        cDto.setDescription(c.getDescription());
        if(c.getCreatedBy() != null) {
            cDto.setCreatedBy(c.getCreatedBy().getUsername());
        }
        if(c.getDateCreated() != null) {
            cDto.setDateCreated(c.getDateCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        return cDto;
    }

    private CaseEntity dtoToEntity(Case c){
        CaseEntity res = new CaseEntity();

        res.setId(c.getId());
        res.setTitle(c.getTitle());
        res.setDescription(c.getDescription());
        if(c.getDateCreated() != null) {
            res.setDateCreated(Date.from(c.getDateCreated().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }

        return res;
    }
}
