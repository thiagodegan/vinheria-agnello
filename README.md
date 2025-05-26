📦 Vinheria Agnello
Aplicativo Android desenvolvido com Kotlin, Jetpack Compose e Room, para gerenciamento de estoque de vinhos e produtos relacionados.

✅ Tecnologias utilizadas
✅ Kotlin 1.9.23

✅ Jetpack Compose (UI moderna e declarativa)

✅ Room (persistência local com SQLite)

✅ ViewModel e StateFlow (arquitetura reativa)

✅ Android Gradle Plugin 8.2.2

✅ Compose BOM 2024.04.00

🚀 Como executar o projeto
1️⃣ Pré-requisitos
Android Studio Hedgehog (2023.1.1) ou superior.

Emulador ou dispositivo físico Android.

JDK 17 configurado.

2️⃣ Clonar o repositório
bash
Copiar
Editar
git clone https://github.com/<SEU_USUARIO>/vinheria-agnello.git
cd vinheria-agnello
3️⃣ Abrir no Android Studio
File → Open → selecione a pasta vinheria-agnello.

Aguarde a indexação e sincronização do Gradle.

4️⃣ Executar
Configure um emulador ou dispositivo físico.

Clique em Run ou use o atalho Shift + F10.

🛠️ Funcionalidades previstas
✅ Configuração com Jetpack Compose
✅ Persistência de dados com Room
✅ CRUD de produtos (Create, Read, Update, Delete)
✅ Listagem de produtos do estoque
✅ Cadastro de novos vinhos

📂 Estrutura inicial do projeto
pgsql
Copiar
Editar
com.vinheriaagnello
│
├── data
│   ├── local (Room: Entity, DAO, Database)
│   └── repository
│
├── domain
│   └── model
│
├── presentation
│   ├── produto (Screens, ViewModel)
│   └── navigation
│
└── MainActivity.kt
🔖 Status
✅ Ambiente configurado
✅ Projeto inicial criado
⬜ Persistência com Room → em andamento
⬜ UI Compose → em andamento

🤝 Contribuição
Pull Requests são bem-vindos!

📝 Licença
MIT

🙌 Autor
Vinheria Agnello
Desenvolvido por: SQUAD 3ESOA