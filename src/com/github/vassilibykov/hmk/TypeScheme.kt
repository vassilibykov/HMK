package com.github.vassilibykov.hmk

/**
 * A monotype with some variables quantified as `forall`.
 * It is possible for the set of quantified variables to be empty,
 * in which case the scheme is actually a stand-in for its underlying
 * monotype.
 */
class TypeScheme(
        private val variables: Set<String>,
        private val type: Monotype
) {
    constructor(type: Monotype) : this(setOf(), type)

    val freeVariables: Set<String> by lazy { type.freeVariables.subtract(variables) }

    fun apply(subst: Substitution): TypeScheme {
        val filtered = subst.without(variables)
        return TypeScheme(variables, type.apply(filtered))
    }

    fun instantiate(): Monotype {
        val newVars = variables.map { it to TVariable.generate() }
        val replacement = Substitution(newVars.toMap())
        return type.apply(replacement)
    }
}