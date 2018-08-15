# Dojo: Recursive grammar expander

This dojo is aimed at programmers of medium to low experience. The goal is to make a grammar expander, that takes a set of rules and produces expansions of them. This dojo is heavily based on [Tracery, by galaxykate](https://github.com/galaxykate/tracery)

## Grammars
For ease of use, the dojo will use JSON files to store grammars.

Each file will consist of a set of key value pairs, with keys being strings and values being lists of strings. The strings may contain expansion symbols. There must be the key ``startpoint`` in this list.

###  Expansion symbols
an expansion symbol is a section of a string surrounded by hashmarks, ``#``. Each expansion symbol in a grammar should exist as a key in the grammars JSON file. for example, ``#animal#`` would be an expansion symbol, and would imply the existence of ``animal`` as a key in the grammar file.

## Expansion
An expansion works by taking a value from the list associated with ``startpoint``, and then searches it for any expansion symbols. Any expansion symbol found will be looked up and a random value from the corresponding list will replace the symbol. If that value itself contains expansion symbols those must be substituted, recurring as necessary. A completed expansion should be a single string, free of expansion symbols.

## Further goals

### Modifiers
Add modifier, that change the formatting of substitutions. for example the expansion symbol ``#animal.capitalize`` should be replaced with an animal, with a capitalized first letter, and it would be pluralized as well if we called it with ``animal.capitalize.s``.

Some suggested modifiers

* capitalize the replacement (use capitalize)
* pluralize the replacement (use s)
* add a or an appropriately  (use a)


### Storage
this section still WIP

Add persistent objects to the grammar. The first time it is encountered, it is evaluated like normal. All subsequent encounters use the value from the first encounter. They should be wrapped in brackets, like ``[#name#]``.

### Weights
this section still WIP
Modify the grammar file to include probabilistic weights given to the options for each expansion symbol.

### Tests
Find a testing library for your language of choice, and write some tests. Try to capture outlying cases, like what happens if an expansion symbol occurs in the beginning of a string, or if performing an expansion causes an expansion symbol to be formed.
