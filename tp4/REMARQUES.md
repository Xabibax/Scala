# Remarques

**livrables ok**

alertes

***compilation - "build completed successfully with 1 warning"
correspondant à 10 feature warnings, ce qui correspond à une multiplication des déclarations de conversions implicites !***

IDE - "Unused import statement" - Les alertes correspondant aux conversions implicites (toutes ces alertes IDE et compilations disparaissent si les conversions implicites sont activées dans l'IDE)

## fp

- tests de base 16/16 (+ 5 tests additionnels)
- tests vérifieur 20/22
  - Le vérifieur accepte "(&& 1 2)" et "(== (&& 1 1) true)". Le problème est que, d'après le vérifieur, les opérateurs logiques acceptent 2 entiers.
  - Il y a un autre problème qui est que les opérandes d'une comparaison ne sont pas vérifiés.
- Le problème de l'implémentation de == n'a été que partiellement résolu (cf commentaires précédents) : "(== 1 2)" n'est pas accepté.

## traits

- Ca semble marcher mais ça échappe complètement à ce qui était demandé, à savoir une implémentation incrémentale des différentes données et opérations, sans modification a fortiori des traits déjà implémentés (et sans recours à de nouvelles conversions implicites).
Ainsi la définition d'IntLit dans le sujet : trait IntLit extends Exp { val v: Int } était complète.
À comparer avec trait IntLit extends ExpEIC { val v: Int } Même chose pour AExp2E ou AExp2I.
