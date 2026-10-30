package graphql;

// Import the business logic classes
import business.ModuleBusiness;
import business.UniteEnseignementBusiness;

// Import the GraphQL library class used to define the root of GraphQL queries
import com.coxautodev.graphql.tools.GraphQLRootResolver;

// Import the entity classes
import entities.Module;
import entities.UniteEnseignement;

import java.util.List;

/**
 * Root resolver for GraphQL queries.
 * It handles queries related to "UniteEnseignement" and "Module".
 */
public class QueryResolver implements GraphQLRootResolver {

    // Business layer helpers
    public UniteEnseignementBusiness helper;
    public ModuleBusiness moduleHelper;

    /**
     * Constructor: initializes both business helpers.
     */
    public QueryResolver() {
        helper = new UniteEnseignementBusiness();
        moduleHelper = new ModuleBusiness();
    }

    // =====================================================
    // Unités d'enseignement
    // =====================================================

    /**
     * query { allUEs { code domaine } }
     */
    public List<UniteEnseignement> allUEs() {
        return helper.getListeUE();
    }

    /**
     * B.2 : query { ueByCode(code: 1) { code domaine } }
     */
    public UniteEnseignement ueByCode(int code) {
        return helper.getUEByCode(code);
    }

    /**
     * B.3 : query { uesByDomaine(domaine: "Informatique") { code domaine } }
     */
    public List<UniteEnseignement> uesByDomaine(String domaine) {
        return helper.getUEByDomaine(domaine);
    }

    /**
     * B.4 : query { uesBySemestre(semestre: 1) { code domaine } }
     */
    public List<UniteEnseignement> uesBySemestre(int semestre) {
        return helper.getUEBySemestre(semestre);
    }

    // =====================================================
    // Modules
    // =====================================================

    /**
     * A.1 : query { allModules { matricule nom } }
     */
    public List<Module> allModules() {
        return moduleHelper.getAllModules();
    }

    /**
     * A.4 : query { modulesByType(type: PROFESSIONNEL) { matricule nom } }
     */
    public List<Module> modulesByType(Module.TypeModule type) {
        return moduleHelper.getModulesByType(type);
    }

    /**
     * A.5 : query { moduleByMatricule(matricule: "M101") { matricule nom } }
     */
    public Module moduleByMatricule(String matricule) {
        return moduleHelper.getModuleByMatricule(matricule);
    }
}