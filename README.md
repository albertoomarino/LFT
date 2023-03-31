# LFT - Linguaggi Formali e Traduttori
Laboratorio di Linguaggi Formali e Traduttori, Corso di Studi in Informatica - A.A. 2021/2022
Dipartimento di Informatica — Università degli Studi di Torino
Docenti: Luigi Di Caro, Viviana Patti e Jeremy Sproston

## Progetto di laboratorio
Il progetto di laboratorio consiste in una serie di esercitazioni assistite mirate allo sviluppo di un semplice traduttore. Il corretto svolgimento di tali esercitazioni presuppone una buona conoscenza del linguaggio di programmazione Java e degli argomenti di teoria del corso Linguaggi Formali e Traduttori.

## Implementazione di un DFA in Java
Lo scopo di questo esercizio `e l’implementazione di un metodo Java che sia in grado di discriminare le stringhe del linguaggio riconosciuto da un automa a stati finiti deterministico (DFA) dato.

### Esercizio 1.1.
Copiare il codice in Listing 1, compilarlo e testarlo su un insieme significativo di stringhe, per es. “010101”, “1100011001”, “10214”, ecc.
Come deve essere modificato il DFA in Figure 1 per riconoscere il linguaggio complementare, ovvero il linguaggio delle stringhe di 0 e 1 che non contengono 3 zeri consecutivi? Progettare e implementare il DFA modificato, e testare il suo funzionamento.

### Esercizio 1.2.
Progettare e implementare un DFA che riconosca il linguaggio degli identificatori in un linguaggio in stile Java: un identificatore è una sequenza non vuota di lettere, numeri, ed il simbolo di “underscore” _ che non comincia con un numero e che non può essere composto solo dal simbolo _. Compilare e testare il suo funzionamento su un insieme significativo di esempi.
Esempi di stringhe accettate: “x”, “flag1”, “x2y2”, “x 1”, “lft lab”, “ temp”, “x 1 y 2”,
“x ”, “ 5”
Esempi di stringhe non accettate: “5”, “221B”, “123”, “9 to 5”, “ ”

### Esercizio 1.3.
Progettare e implementare un DFA che riconosca il linguaggio di stringhe che contengono un numero di matricola seguito (subito) da un cognome, dove la combinazione di matricola e cognome corrisponde a studenti del corso A che hanno un numero di matricola pari oppure a studenti del corso B che hanno un numero di matricola dispari. Si ricorda che gli studenti del corso A sono quelli con cognomi la cui iniziale è compresa tra A e K, e gli studenti del corso B sono quelli con cognomi la cui iniziale è compresa tra L e Z.
Per esempio, “123456Bianchi” e “654321Rossi” sono stringhe del linguaggio, mentre “654321Bianchi” e “123456Rossi” no. Nel contesto di questo esercizio, un numero di ma-
tricola non ha un numero prestabilito di cifre (ma deve essere composto di almeno una cifra). Un cognome corrisponde a una sequenza di lettere, e deve essere composto di almeno una lettera. Quindi l’automa deve accettare le stringhe “2Bianchi” e “122B” ma non “654322” e “Rossi”.
Assicurarsi che il DFA sia minimo.

### Esercizio 1.4.
Modificare l’automa dell’esercizio precedente in modo che riconosca le combinazioni di matricola e cognome degli studenti del corso A che hanno un numero di matricola pari oppure a studenti del corso B che hanno un numero di matricola dispari, dove il numero di matricola e il cognome possono essere separati da una sequenza di spazi, e possono essere precedute e/o seguite da sequenze eventualmente vuote di spazi.
Per esempio, l’automa deve accettare la stringa “654321 Rossi” e “ 123456 Bianchi ” (dove, nel secondo esempio, ci sono spazi prima del primo carattere e dopo l’ultimo carattere), ma non “1234 56Bianchi” e “123456Bia nchi”. Per questo esercizio, i cognomi composti (con un numero arbitrario di parti) possono essere accettati: per esempio, la stringa “123456De Gasperi” deve essere accettato.
Modificare l’implementazione Java dell’automa di conseguenza.

### Esercizio 1.5.
Progettare e implementare un DFA che, come in Esercizio 1.3, riconosca il linguaggio di stringhe che contengono matricola e cognome degli studenti del corso A che hanno un numero di matricola pari oppure a studenti del corso B che hanno un numero di matricola dispari, ma in cui il cognome precede il numero di matricola (in altre parole, le posizioni del cognome e matricola sono scambiate rispetto all’Esercizio 1.3). Assicurarsi che il DFA sia minimo.

### Esercizio 1.6.
Progettare e implementare un DFA che riconosca il linguaggio di stringhe che contengono un numero di matricola seguito (subito) da un cognome, dove la combinazione di matricola e cognome corrisponde a studenti del turno T2 o del turno T3 del laboratorio di Linguaggi Formali e Traduttori. Si ricorda le regole per suddivisione di studenti in turni:
• Turno T1: cognomi la cui iniziale è compresa tra A e K, e la penultima cifra del numero di matricola è dispari;  
• Turno T2: cognomi la cui iniziale è compresa tra A e K, e la penultima cifra del numero di matricola è pari;  
• Turno T3: cognomi la cui iniziale è compresa tra L e Z, e la penultima cifra del numero di matricola è dispari;  
• Turno T4: cognomi la cui iniziale è compresa tra L e Z, e la penultima cifra del numero di matricola è pari.
Un numero di matricola deve essere composto di almeno due cifre, ma (come in Esercizio 1.3) non ha un numero massimo prestabilito di cifre. Assicurarsi che il DFA sia minimo.
Esempi di stringhe accettate: “654321Bianchi”, “123456Rossi”, “221B” Esempi di stringhe non accettate: “123456Bianchi”, “654321Rossi”, “5”, “654322”, “Rossi”, “2Bianchi”

### Esercizio 1.7.
Progettare e implementare un DFA che riconosca il linguaggio di stringhe che contengono il tuo nome e tutte le stringhe ottenute dopo la sostituzione di un carattere del nome con un altro qualsiasi. Ad esempio, nel caso di uno studente che si chiama Paolo, il DFA deve accettare la stringa “Paolo” (cio`e il nome scritto correttamente), ma anche le stringhe “Pjolo”, “caolo”, “Pa%lo”, “Paola” e “Parlo” (il nome dopo la sostituzione di un carattere), ma non “Eva”, “Perro”, “Pietro” oppure “P*o*o”.

### Esercizio 1.8.
Progettare e implementare un DFA che riconosca il linguaggio delle costanti numeriche in virgola mobile utilizzando la notazione scientifica dove il simbolo e indica la funzione esponenziale con base 10. L’alfabeto del DFA contiene i seguenti elementi: le cifre numeriche 0, 1, . . . , 9, il segno . (punto) che precede una eventuale parte decimale, i segni + (più) e - (meno) per indicare positività o negatività, e il simbolo e. Le stringhe accettate devono seguire le solite regole per la scrittura delle costanti numeriche. In particolare, una costante numerica consiste di due segmenti, la seconda di quale `e opzionale: il primo segmento `e una sequenza di cifre numeriche che (1) pu `o essere preceduta da un segno + o meno -, (2) pu `o essere seguita da un segno punto ., che a sua volta deve essere seguito da una sequenza non vuota di cifre numeriche; il secondo segmento inizia con il simbolo e, che a sua volta `e seguito da una sequenza di cifre numeriche che soddisfa i punti (1) e (2) scritti per il primo segmento. Si nota che, sia nel primo segmento, sia in un eventuale secondo segmento, un segno punto . non deve essere preceduto per forza da una cifra numerica.
Esempi di stringhe accettate: “123”, “123.5”, “.567”, “+7.5”, “-.7”, “67e10”, “1e-2”, “-.7e2”, “1e2.3”
Esempi di stringhe non accettate: “.”, “e3”, “123.”, “+e6”, “1.2.3”, “4e5e6”, “++3”

### Esercizio 1.9.
Progettare e implementare un DFA con alfabeto {/, *, a} che riconosca il linguaggio di “commenti” delimitati da /* (all’inizio) e */ (alla fine): cio`e l’automa deve accettare le stringhe che contengono almeno 4 caratteri che iniziano con /*, che finiscono con */, e che contengono una sola occorrenza della sequenza */, quella finale (dove l’asterisco della sequenza */non deve essere in comune con quello della sequenza /* all’inizio).
Esempi di stringhe accettate: “/****/”, “/*a*a*/”, “/*a/**/”, “/**a///a/a**/”, “/**/”, “/*/*/”
Esempi di stringhe non accettate: “/*/”, “/**/***/”

### Esercizio 1.10. Modificare l’automa dell’esercizio precedente in modo che riconosca il linguaggio di stringhe (sull’alfabeto {/, *, a}) che contengono “commenti” delimitati da /* e */, ma con la possibilità di avere stringhe prima e dopo come specificato qui di seguito. L’idea `e che sia possibile avere eventualmente commenti (anche multipli) immersi in una sequenza di simboli dell’alfabeto. Quindi l’unico vincolo `e che l’automa deve accettare le stringhe in cui un’occorrenza della sequenza /* deve essere seguita (anche non immediatamente) da un’occorrenza della sequenza */. Le stringhe del linguaggio possono non avere nessuna occorrenza della sequenza
/* (caso della sequenza di simboli senza commenti). Implementare l’automa seguendo la costruzione vista in Listing 1.
Esempi di stringhe accettate: “aaa/****/aa”, “aa/*a*a*/”, “aaaa”, “/****/”, “/*aa*/”, “*/a”, “a/**/***a”, “a/**/***/a”, “a/**/aa/***/a”
Esempi di stringhe non accettate: “aaa/*/aa”, “a/**//***a”, “aa/*aa”


## Analisi lessicale
Gli esercizi di questa sezione riguardano l’implementazione di un analizzatore lessicale per un semplice linguaggio di programmazione. Lo scopo di un analizzatore lessicale `e di leggere un testo e di ottenere una corrispondente sequenza di token, dove un token corrisponde ad un’unità lessicale, come un numero, un identificatore, un operatore relazionale, una parola chiave, ecc. Nelle sezioni successive, l’analizzatore lessicale da implementare sar`a poi utilizzato per fornire l’input a programmi di analisi sintattica e di traduzione.
Gli identificatori corrispondono all’espressione regolare: (a + ... + z + A + ... + Z)(a + ... + z + A + ... + Z + 0 + ... + 9)∗ e i numeri corrispondono all’espressione regolare 0 + (1 + ... + 9)(0 + ... + 9)∗. L’analizzatore lessicale dovr`a ignorare tutti i caratteri riconosciuti come “spazi” (incluse le tabulazioni e i ritorni a capo), ma dovr`a segnalare la presenza di caratteri illeciti, quali ad esempio o @.

### Esercizio 2.1.
Si scriva in Java un analizzatore lessicale che legga da file un input e stampi la sequenza di token corrispondente. Per questo esercizio, si possono utilizzare senza modifica le classi Tag, Token e Word. Invece le classi NumberTok e Lexer devono essere completate.

### Esercizio 2.2.
Consideriamo la seguente nuova definizione di identificatori: un identificatore è una sequenza non vuota di lettere, numeri, ed il simbolo di “underscore” _ ; la sequenza non
comincia con un numero e non pu `o essere composta solo dal simbolo _.

### Esercizio 2.3.
Estendere il metodo lexical_scan in modo tale che possa trattare la presenza di commenti nel file di input. I commenti possono essere scritti in due modi:
• commenti delimitati con /* e */;
• commenti che iniziano con // e che terminano con un a capo oppure con EOF.
I commenti devono essere ignorati dal programma per l’analisi lessicale; in altre parole, per le parti dell’input che contengono commenti, non deve essere generato nessun token. Ad esempio, consideriamo l’input seguente.
/* calcolare la velocita‘ */
assign 300 to d; // distanza
assign 10 to t; // tempo
print(* d t)
L’output del programma per l’analisi lessicale sar`a ⟨259, assign⟩ ⟨256, 300⟩ ⟨260, to⟩ ⟨257, d⟩ ⟨59⟩ ⟨259, assign⟩ ⟨256, 10⟩ ⟨260, to⟩ ⟨257, t⟩ ⟨59⟩ ⟨266, print⟩ ⟨40⟩ ⟨42⟩ ⟨257, d⟩ ⟨257, t⟩ ⟨41⟩ ⟨−1⟩. Oltre alle coppie di simboli /*, */ e //, un commento pu `o contenere simboli che non fanno parte del pattern di nessun token (ad esempio, /*@#?*/ o /*calcolare la velocita‘*/). Se un commento di forma /* ... */ `e aperto ma non chiuso prima della fine del file (si veda ad esempio il caso di input assign 300 to d /*distanza ) deve essere segnalato un errore. Si noti che ci possono essere pi `u commenti consecutivi non separati da nessun token, ad esempio: assign 300 to d /*distanza*//*da Torino a Lione*/ Inoltre la coppia di simboli */, se scritta al di fuori di un commento, deve essere trattata dal lexer come il segno di moltiplicazione seguito dal segno di divisione (ad esempio, per l’input x*/y l’output sar`a ⟨257, x⟩ ⟨42⟩ ⟨47⟩ ⟨257, y⟩ ⟨−1⟩). In altre parole, l’idea `e che in questo caso la sequenza di simboli */ non verr`a interpretata come la chiusura di un commento ma come una sequenza dei due token menzionati.
