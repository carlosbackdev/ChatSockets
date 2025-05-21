# 💬 ChatSockets 🔗

A **Java-based Client-Server Chat Application** leveraging sockets, multi-threading, and the **Observer pattern** for real-time communication!  
Created by [carlosbackdev](https://github.com/carlosbackdev)

---

## 📚 Theoretical Background

### 1. Client/Server Architecture 🖥️↔️🖥️
This project uses a classic Client/Server model:

- **Server**: Provides services, always listening for client requests.
- **Client**: Connects to server, sends/receives messages.

➡️ Multiple clients can connect to the server simultaneously. The server handles the communication, resource sharing, and connection management.

### 2. Socket Communication & Observer Pattern in Java 🧩📡

- **Sockets** enable direct communication between applications over a network.
- **Observer Pattern** used via `MensajeListener` and `MensajesEscucha`:
  - Decouples network events from GUI logic.
  - Automatically triggers UI updates when a message arrives.

### 3. Multi-threading for Real-Time Communication 🧵⚡

- The server creates a **dedicated thread** per client to allow asynchronous communication.
- Clients listen for server messages in a **separate listener thread**, so the UI stays responsive.

---

## 🚀 Features

- 🤖 Intuitive JavaFX GUI for both server and client.
- 🔁 Supports multiple simultaneous clients.
- 📢 Broadcast messages from server to all clients.
- 📡 Real-time communication and connection updates.
- 📝 Console logging of all key events.
- 💾 Executable JAR for cross-platform deployment.
- 🧼 Clean, modular, and scalable code structure.

---

## 🛠️ Technologies Used

- ✅ Java 17  
- 🎨 JavaFX 17  
- ⚙️ Maven  
- 🌐 TCP Sockets  
- 🧠 Threads & ExecutorService  
- 📶 Observer Pattern  
- 📄 FXML

---

🌐 Networking Tips
Set Linux VM to bridge mode or configure NAT + port forwarding.

Ping Linux IP from Windows to confirm connectivity.

Use the Linux IP (e.g., 10.0.3.32) and the configured port when launching the client.

Launch multiple client instances for group chat testing.

---

# 📷 Screenshot

![Socket Java](https://github.com/user-attachments/assets/b3b147af-824d-400f-b735-f6bd363ccc2e)


---

✅ Improvements & Future Ideas
🔐 User login & authentication

🧠 Use JavaFX CSS for modern UI


🙌 Credits
Developed with ☕ and ❤️ by Carlos Arroyo García
