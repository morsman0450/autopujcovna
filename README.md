# Systém pro Půjčovnu Aut - Backendové Řešení

## Přehled

Tento repozitář obsahuje backendové řešení pro systém půjčovny aut. Systém je postaven na frameworku Spring a poskytuje funkce pro správu vozového parku, zaznamenávání půjčoven a správu dat zákazníků prostřednictvím RESTful API.

## Struktura Projektu

- **Spring Boot Aplikace**: Aplikace je postavena na Spring Boot a využívá RESTful endpointy pro správu půjčoven aut.
- **Maven**: Projekt používá Maven jako nástroj pro sestavení a správu závislostí.
- **JUnit & MockMvc**: Testy jsou psány pomocí JUnit a MockMvc pro simulaci HTTP požadavků a testování funkcionality.

## Funkce

- **Správa Aut**: Umožňuje správu dostupných aut pro půjčení.
- **Správa Zákazníků**: Spravuje údaje o zákaznících.
- **Půjčování**: Umožňuje zákazníkům půjčit si auto a spravuje záznamy o půjčování, včetně cen a dat návratů.
- **Validace**: Zajišťuje, že auto nemůže být půjčeno, pokud je již rezervováno.

## Závislosti

- **Spring Boot**: Hlavní framework pro vývoj aplikace.
- **Spring Data JPA**: Pro správu databáze a mapování entit.
- **JUnit**: Testovací framework pro psaní unit testů.
- **MockMvc**: Pro simulaci HTTP požadavků v testech.
- **Maven**: Nástroj pro správu závislostí a sestavení projektu.
