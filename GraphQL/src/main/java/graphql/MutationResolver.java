package graphql;

// Import the business logic classes
import business.ModuleBusiness;
import business.UniteEnseignementBusiness;

// Import the GraphQL library class used to define the root of GraphQL mutations
import com.coxautodev.graphql.tools.GraphQLRootResolver;

// Import the entity classes
import entities.Module;
import entities.UniteEnseignement;

/**
 * This class defines all GraphQL mutations related to "UniteEnseignement" and "Module".
 * A mutation in GraphQL is used to perform write operations (create, update, delete)
 * as opposed to queries, which are for read operations.
 */
public class MutationResolver implements GraphQLRootResolver {

    // Attributes used to access the business logic (service layer)
    public UniteEnseignementBusiness helper;
    public ModuleBusiness moduleHelper;

    /**
     * Constructor of the resolver.
     * It initializes the business helpers that manage the teaching units and the modules.
     */
    public MutationResolver() {
        helper = new UniteEnseignementBusiness();
        moduleHelper = new ModuleBusiness();
    }

    // =====================================================
    // Unités d'enseignement
    // =====================================================

    /**
     * GraphQL Mutation: addUniteEnseignement
     *
     * mutation {
     *     addUniteEnseignement(code: 101, domaine: "Informatique",
     *                          responsable: "Dr. Smith", credits: 4, semestre: 2)
     * }
     */
    public boolean addUniteEnseignement(int code, String domaine, String responsable, int credits, int semestre) {
        UniteEnseignement ue = new UniteEnseignement(code, domaine, responsable, credits, semestre);
        return helper.addUniteEnseignement(ue);
    }

    /**
     * GraphQL Mutation: updateUniteEnseignement (B.5)
     */
    public boolean updateUniteEnseignement(int code, String domaine, String responsable, int credits, int semestre) {
        UniteEnseignement ue = new UniteEnseignement(code, domaine, responsable, credits, semestre);
        return helper.updateUniteEnseignement(code, ue);
    }

    /**
     * GraphQL Mutation: deleteUniteEnseignement (B.6)
     */
    public boolean deleteUniteEnseignement(int code) {
        return helper.deleteUniteEnseignement(code);
    }

    // =====================================================
    // Modules
    // =====================================================

    /**
     * GraphQL Mutation: addModule (A.2)
     *
     * mutation {
     *     addModule(matricule: "UE123", libelle: "SOA", coef: 2,
     *               volumeHoraire: 21, codeUE: 1, type: TRANSVERSAL)
     * }
     *
     * The order of the parameters must match the order in schema.graphql.
     */
    public boolean addModule(String matricule, String libelle, int coef, int volumeHoraire,
                             Module.TypeModule type, int codeUE) {
        // ModuleBusiness.addModule reads the UE code, then looks up the full UE
        UniteEnseignement ue = new UniteEnseignement();
        ue.setCode(codeUE);

        Module m = new Module(matricule, libelle, coef, volumeHoraire, type, ue);
        return moduleHelper.addModule(m);
    }

    /**
     * GraphQL Mutation: updateModule (A.3)
     */
    public Module updateModule(String matricule, String libelle, int coef, int volumeHoraire,
                               Module.TypeModule type, int codeUE) {
        UniteEnseignement ue = helper.getUEByCode(codeUE);
        if (ue == null) {
            return null;
        }
        Module m = new Module(matricule, libelle, coef, volumeHoraire, type, ue);
        return moduleHelper.updateModule(matricule, m) ? m : null;
    }

    /**
     * GraphQL Mutation: deleteModule (A.6)
     */
    public String deleteModule(String matricule) {
        return moduleHelper.deleteModule(matricule) ? "deleted successfully" : "module not found";
    }
}