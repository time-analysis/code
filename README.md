# code
Project for Advanced Software Engineering.

# Idea
The application should provide analysis for the theorie phase of several semesters.

## Use Cases:
Basic:
* addEntry(Duration)
* addLecture(...)
* addSemester(...)
* getEntryTime()
done:
* addEntry(start, stop) -> optional timer-feeling over UI
---
User Administration
* createUser(name, password)
* loginUser(name, password)
* addSemester(...)
---
Analyse:
* timePerLecture(Lecture)
* getPresenceTime()
* getStudyTime()
* compareTime()
* timePerSemester

## Dependencies
kurze Antwort:
ob es ein Listener sein muss oder einfach nur eine Abstraktion durch ein Interface inklusive Dependency Injection liegt bei euch. Ich schätze ein Interface mit Dependency Injection reicht aus.

lange Antwort:
Ein Listener wird im Wesentlichen durch zwei Eigenschaften gekennzeichnet.
1. er wird von einem anderen Objekt über einen "Sachverhalt" informiert
2. er kann während der Laufzeit einem Objekt zugewiesen werden und diese Zuweisung kann aber auch wieder gelöscht werden.

In eurem Fall ist der zweite Punkt vermutlich nicht notwendig. D.h. die "Registrierung" findet in Form einer Zuweisung bei der Instanzierung des Use-Cases statt. Folgender Aufbau wäre denkbar:

![grafik](https://user-images.githubusercontent.com/85218663/200311841-d97711a7-5b1a-4a67-ad0f-5afb00d86397.png)

Beim Starten eurer Anwendung findet die Instanzierung der Klassen dann wie folgt statt:
1. CsvAusgabe instanzieren
2. DatenspeicherMapping instanzieren und CsvAusgabe im Konstruktor übergeben
3. Use-Case-Klasse instanzieren und DatenspeicherMapping im Konstruktor übergeben

Die Use-Case-Klasse und die DatenspeicherMapping-Klasse kennen so nur eine Abstraktion und sind nicht weiter von Details abhängig. Zur Speicherung rufen sie einfach speichere- bzw. schreibeDaten-Methode auf.

Damit müsst ihr kein komplettes Listener-Muster implementieren, sondern lediglich die Aufrufe zum Speichern an die äußeren Schichten delegieren.
