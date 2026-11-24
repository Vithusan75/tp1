package org.ldv.todoapp.controller
import org.ldv.todoapp.controller.form.TacheForm
import org.ldv.todoapp.model.entity.Tache
import org.ldv.todoapp.model.entity.Utilisateur
import org.ldv.todoapp.model.repository.UtilisateurRepository
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import jakarta.servlet.http.HttpSession
import org.ldv.todoapp.model.repository.TacheRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MesTachesController(
    private val tacheRepository: TacheRepository
) {

    @GetMapping("/mes-taches")
    fun mesTaches(
        model: Model,
        session: HttpSession
    ): String {

        // Récupération de l'identifiant de l'utilisateur actif
        val utilisateurId =
            session.getAttribute("utilisateurId") as? Long

        // Récupération des tâches de l'utilisateur actif
        val taches = if (utilisateurId != null) {
            tacheRepository.findByUtilisateur_IdUtilisateur(utilisateurId)
        } else {
            emptyList()
        }

        // Transmission de la liste des tâches au modèle
        model.addAttribute("taches", taches)

        // Affichage du template mes-taches.html
        return "mes-taches"
    }
    @Controller
    class MesTachesController(
        private val tacheRepository: TacheRepository,
        private val utilisateurRepository: UtilisateurRepository
    ) {
// Méthode mesTaches() existante et nouvelles méthodes.
private fun rechercherUtilisateurActif(session: HttpSession): Utilisateur? {
    val utilisateurId = session.getAttribute("utilisateurId") as? Long ?: return null
    val utilisateur = utilisateurRepository.findById(utilisateurId).orElse(null)
    if (utilisateur == null) {
        session.removeAttribute("utilisateurId")
    }
    return utilisateur
}
        @GetMapping("/mes-taches/creer")
        fun formulaireCreation(
            model: Model, session: HttpSession,
            redirectAttributes: RedirectAttributes
        ): String {
            if (rechercherUtilisateurActif(session) == null) {
                redirectAttributes.addFlashAttribute("messageErreur",
                    "Sélectionnez un utilisateur avant de créer une tâche.")
                return "redirect:/mes-taches"
            }
            model.addAttribute("tacheForm", TacheForm())
            return "formulaire-tache"
        }
    }
}
