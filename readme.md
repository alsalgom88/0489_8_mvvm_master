
# 🧮 Activitat MVVM — Calculadora de quota d’hipoteca

## 📚 Context
Aquesta activitat forma part del mòdul **0487 / 0489 – Entorns de desenvolupament / Programació multimèdia**  
i està orientada a alumnat de **DAM2**, amb coneixements previs de programació.

L’objectiu **no és fer una simple calculadora**, sinó **entendre i aplicar correctament l’arquitectura MVVM en Android** amb Kotlin i XML.

---

## 🎯 Objectius de la pràctica

- Entendre **per què MVVM és necessari** en Android modern
- Separar correctament:
  - **View**
  - **ViewModel**
  - **Model**
- Evitar problemes habituals:
  - Bloqueig de la UI (ANR)
  - Pèrdua de dades en rotació de pantalla
  - Memory leaks
- Gestionar:
  - Execució en segon pla
  - Estat observable
  - Progrés
  - Errors de negoci

---

## 🧠 Què és MVVM (resum)

**MVVM (Model – View – ViewModel)** és un patró arquitectònic que separa clarament responsabilitats:

- **View** → mostra dades i reacciona a l’estat
- **ViewModel** → conté l’estat i la lògica de presentació
- **Model** → conté la lògica de negoci i les regles

📌 La View **no calcula**, **no decideix** i **no executa fils**.

---

## 🏗️ Estructura COMPLETA del projecte
```
app/
├─ manifests/
│  └─ AndroidManifest.xml
│
├─ kotlin+java/
│  └─ com.example.mvvm_master/
│     ├─ ui/
│     │  └─ MiHipotecaFragment.kt        → View (UI)
│     │
│     ├─ viewmodel/
│     │  └─ MiHipotecaViewModel.kt       → ViewModel
│     │
│     ├─ model/
│     │  └─ SimuladorHipoteca.kt         → Model (negoci)
│     │
│     └─ MainActivity.kt                 → Activity contenidor
│
├─ res/
│  ├─ layout/
│  │  ├─ activity_main.xml               → Contenidor de navegació
│  │  └─ fragment_mi_hipoteca.xml        → UI del fragment
│  │
│  ├─ navigation/
│  │  └─ nav_graph.xml                   → Grafo de navegació
│  │
│  ├─ values/
│  │  ├─ colors.xml
│  │  ├─ strings.xml
│  │  └─ themes.xml
│  │
│  ├─ drawable/
│  ├─ mipmap/
│  └─ xml/
│
├─ Gradle Scripts/
│  ├─ build.gradle.kts (Project)
│  ├─ build.gradle.kts (Module: app)
│  ├─ gradle.properties
│  └─ proguard-rules.pro
```
---
## 📂 Estructura del projecte (Android Studio)

![Estructura del projecte](img1.png)

---

## 🧩 Components del projecte

### 🧭 MainActivity
- **No conté lògica**
- Actua només com a **contenidor de Fragments**
- Allotja el `NavHostFragment`

📌 En Android modern, l’Activity **no és la UI principal**.

---

## 🖥️ View (UI)

### 📄 MiHipotecaFragment
**Responsabilitats:**
- Llegir dades introduïdes per l’usuari
- Observar `LiveData` del ViewModel
- Mostrar:
  - Resultat
  - Errors
  - Progrés

❌ No calcula  
❌ No fa lògica de negoci  
❌ No gestiona fils  

---

## 🧠 ViewModel

### 📄 MiHipotecaViewModel
**Responsabilitats:**
- Ser el **propietari de l’estat de la pantalla**
- Executar operacions en segon pla
- Exposar estat observable (`LiveData`)
- Comunicar:
  - Resultat
  - Errors
  - Progrés

📌 El ViewModel **sobreviu a la rotació de pantalla**.

---

## ⚙️ Model

### 📄 SimuladorHipoteca
**Responsabilitats:**
- Rebre dades d’entrada
- Aplicar regles de negoci
- Calcular la quota hipotecària
- Simular una operació lenta (sleep)

📌 El Model:
- No coneix Fragments
- No coneix LiveData
- No depèn d’Android UI

És reutilitzable en:
- Aplicacions de consola
- Backend
- Tests unitaris

---

## ⏳ Execució en segon pla

- El càlcul **no s’executa al fil principal**
- El ViewModel utilitza un `Executor`
- El resultat es publica amb `postValue`

Això evita:
- Bloquejos de la UI
- Errors ANR

---

## 🔄 Estat observable

L’app utilitza `LiveData` per representar estat:

- Quota calculada
- Progrés (`calculant`)
- Errors (capital, termini, etc.)

📌 La View **no pregunta**, **reacciona**.

---

## 🚦 Gestió del progrés

- El Model notifica inici i fi del càlcul
- El ViewModel ho tradueix a estat observable
- La View mostra o amaga la `ProgressBar`

📌 El progrés **és estat**, no decoració.

---

## ❌ Errors i validació

- **Errors de negoci** → Model
- **Gestió d’errors** → ViewModel
- **Errors de format (inputs)** → View

📌 Separació clara de responsabilitats (clau DAM2).

---

## 🚀 Tecnologies utilitzades

- Kotlin
- XML
- ViewBinding
- ViewModel
- LiveData
- Navigation Component

---

## 📌 Notes finals

Aquesta pràctica és una **base sòlida i realista** per:
- Apps més grans
- Consum d’API REST
- Persistència amb Room
- Tests unitaris
- Arquitectures escalables

> **“La View mostra, el Model calcula, el ViewModel connecta.”**

---

## 🛠️ Modificacions obligatòries sobre el projecte base

El projecte proporcionat **NO és una solució final**.  
És una **base arquitectònica correcta** sobre la qual hauràs d’implementar millores **respectant estrictament l’arquitectura MVVM**.

Aquestes modificacions **formen part de l’avaluació** i no es poden resoldre amb simples canvis visuals.

---

### 1️⃣ Interès variable segons el termini

#### Requisit funcional
El tipus d’interès **NO pot ser fix**.  
Ha de variar segons el nombre d’anys de la hipoteca.

#### Condicions mínimes
- **< 15 anys** → interès més baix  
- **15 – 25 anys** → interès mitjà  
- **> 25 anys** → interès més alt  

#### Requisit arquitectònic
📍 **La lògica d’interès va exclusivament al Model**

✔️ `SimuladorHipoteca`  
❌ `MiHipotecaFragment`  
❌ `MiHipotecaViewModel`

📌 El ViewModel **no decideix interessos**, només gestiona estat i comunicació amb la View.

---

### 2️⃣ Nou error de negoci: capital màxim

#### Requisit funcional
Si el capital sol·licitat supera un límit (per exemple **500.000 €**):
- El càlcul **NO s’executa**
- L’usuari ha de veure un **missatge d’error clar**

#### Requisit arquitectònic
- El **Model detecta l’error**
- El **ViewModel l’exposa com a `LiveData`**
- La **View només mostra l’error**

🚫 No es permet:
- Mostrar `Toast` des del Model
- Validació de negoci al Fragment
- Decisions de negoci a la View

📌 Els errors també són **estat observable**.

---

### 3️⃣ Bloqueig del botó mentre es calcula

#### Requisit funcional
Mentre el càlcul està en curs:
- El botó **Calcular** ha d’estar **desactivat**
- El progrés ha de ser **visible**

📌 El botó **NO reacciona a callbacks directes**  
📌 El botó **reacciona a l’estat exposat pel ViewModel**

Això evita:
- Accions duplicades
- Errors d’estat
- Mala experiència d’usuari

---

### 4️⃣ Barra superior visible (AppBar / Toolbar)

#### Requisit funcional
L’aplicació ha de mostrar **una barra superior fixa** amb el títol:

> **Quota Hipoteca**

Tal com es veu a la imatge següent:


![Barra superior de l'app](img4.png)


#### Requisit tècnic

* La barra **NO pot estar dins del Fragment**
* Ha de formar part de l’`Activity` o del layout base
* Ha de ser coherent amb el sistema de navegació

📌 Aquesta modificació reforça un principi clau:

> **L’Activity és contenidor i marc de navegació, no lògica.**

---

## 🔹 Modificacions opcionals (escull mínim 1)

### 🔸 A) Cancel·lació del càlcul

* Afegir un botó **Cancel·lar**
* En cancel·lar:

  * No es mostra cap resultat
  * El progrés s’atura correctament

---

### 🔸 B) Simulació d’error de servidor

* El Model pot fallar aleatòriament
* L’error s’exposa com a estat observable
* L’aplicació **NO pot fer crash**

---

### 🔸 C) Persistència de l’últim càlcul

* Guardar capital, termini i quota
* Recuperar-los en reobrir l’app

---

### 🔸 D) Refactorització a Coroutines (nivell avançat)

* Substituir `Executor` per `viewModelScope`
* Mantenir exactament el mateix comportament observable

---

## 🚫 Prohibicions explícites

❌ Lògica de negoci al Fragment
❌ Càlculs al fil principal
❌ Accés directe del Fragment al Model
❌ Ús de `AsyncTask`
❌ Solucions “ràpides” fora de MVVM

📌 Si una funcionalitat funciona però està a la capa incorrecta, **està malament**.
---