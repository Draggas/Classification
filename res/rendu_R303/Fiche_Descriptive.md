Fiche Descriptive 1
-------------------

- **Système**: Système de visualisation et classification
- **Cas d'utilisation**: Charger l'ensemble de données

- **Acteur principal**: Client
- **Déclencheur**: /
- **Autre acteurs**: /

- **Préconditions**: Le client doit fournir les données sous forme de fichiers CSV.
- **Garanties en cas de succès**: Les données sont enregistrées. Le modèle est construit à partir de ces données.
- **Garanties minimales**: Si le scénario ne finit pas, aucune donnée sera enregistré et le modèle restera vide.

- **Scénario nominal**: 

1. L'utilisateur sélectionne la fonctionnalité "faire un don"
2. L'utilisateur spécifie le projet et la somme qu'il veut donner
3. Il saisit si il veut profiter de la contrepartie offerte et valide
4. Le système affiche le formulaire de saisie de paiement
5. L'utilisateur rentre ses informations de paiement et valide
6. Le système envoie une requête (ordre de paiement) au service de paiement
7. Le service de paiement confirme le paiement
8. Le système enregistre le don et met à jour la cagnotte de ce projet et envoie un message de confirmation à l'utilisateur.

- **Scénarios alternatifs**:
    + **A** : A l'étape 7, le paiement est refusé
    - 7(A) : Le service de paiement refuse le paiement et affiche un message d'erreur informant que le paiement a été refusé par le service de paiement.
    - 8(A) : Retour à l'étape 5

-------------------
Fiche Descriptive 2
-------------------

- **Système**: Système de visualisation et classification
- **Cas d'utilisation**: Ajouter une donnée

- **Acteur principal**: Client
- **Déclencheur**: /
- **Autre acteurs**: /

- **Préconditions**: /
- **Garanties en cas de succès**: /
- **Garanties minimales**: /

- **Scénario nominal**: 

1.
2.
3.

- **Scénarios alternatifs**:
    + **A** : /
    - ?(A) : /
    - ?(A) : /

-------------------
Fiche Descriptive 3
-------------------

- **Système**: Système de visualisation et classification
- **Cas d'utilisation**: Ajouter une donnée

- **Acteur principal**: Client
- **Déclencheur**: /
- **Autre acteurs**: /

- **Préconditions**: /
- **Garanties en cas de succès**: /
- **Garanties minimales**: /

- **Scénario nominal**: 

1.
2.
3.

- **Scénarios alternatifs**:
    + **A** : /
    - ?(A) : /
    - ?(A) : /