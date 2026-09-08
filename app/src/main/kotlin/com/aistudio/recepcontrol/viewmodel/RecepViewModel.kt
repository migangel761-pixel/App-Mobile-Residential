package com.aistudio.recepcontrol.viewmodel

import androidx.lifecycle.ViewModel
import com.aistudio.recepcontrol.model.Announcement
import com.aistudio.recepcontrol.model.PhysicalReceipt
import com.aistudio.recepcontrol.model.PhysicalReceiptStatus
import com.aistudio.recepcontrol.model.ResidentNotification
import com.aistudio.recepcontrol.model.User
import com.aistudio.recepcontrol.model.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecepViewModel : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    // Recent notification popups/alerts for resident
    private val _notifications = MutableStateFlow<List<ResidentNotification>>(
        listOf(
            ResidentNotification(
                id = "notif_init_1",
                title = "¡Nuevo Recibo Físico en Portería!",
                message = "El guarda Carlos Mendoza ha recibido el recibo de Energía Eléctrica (Enel) para tu apartamento Torre 1 - 302.",
                timestamp = "Hoy, 10:15 AM",
                apartment = "Torre 1 - 302",
                receiptId = "rec_1"
            )
        )
    )
    val notifications: StateFlow<List<ResidentNotification>> = _notifications.asStateFlow()

    private val _activeAlert = MutableStateFlow<ResidentNotification?>(null)
    val activeAlert: StateFlow<ResidentNotification?> = _activeAlert.asStateFlow()

    private val _receipts = MutableStateFlow<List<PhysicalReceipt>>(
        listOf(
            PhysicalReceipt(
                id = "rec_1",
                serviceCategory = "Energía Eléctrica (Enel)",
                apartment = "Torre 1 - 302",
                status = PhysicalReceiptStatus.EN_PORTERIA,
                referenceCode = "REC-ENEL-8842",
                receivedByGuard = "Carlos Mendoza",
                receivedTimestamp = "Hoy, 10:15 AM",
                guardNote = "Sobre original sellado de Enel Colombia, clasificado en casillero 302.",
                hasPhoto = true
            ),
            PhysicalReceipt(
                id = "rec_2",
                serviceCategory = "Acueducto y Alcantarillado (EAAB)",
                apartment = "Torre 1 - 302",
                status = PhysicalReceiptStatus.EN_PORTERIA,
                referenceCode = "REC-EAAB-1903",
                receivedByGuard = "Carlos Mendoza",
                receivedTimestamp = "Ayer, 04:30 PM",
                guardNote = "Factura bimestral de agua. Dejada en custodia en portería principal.",
                hasPhoto = true
            ),
            PhysicalReceipt(
                id = "rec_3",
                serviceCategory = "Gas Natural (Vanti)",
                apartment = "Torre 1 - 302",
                status = PhysicalReceiptStatus.ENTREGADO,
                referenceCode = "REC-VAN-4521",
                receivedByGuard = "Carlos Mendoza",
                receivedTimestamp = "05 Sep, 02:10 PM",
                deliveredTimestamp = "05 Sep, 06:45 PM",
                guardNote = "Entregado personalmente al residente en puerta.",
                hasPhoto = true
            ),
            PhysicalReceipt(
                id = "rec_4",
                serviceCategory = "Telecomunicaciones (Claro)",
                apartment = "Torre 2 - 104",
                status = PhysicalReceiptStatus.EN_PORTERIA,
                referenceCode = "REC-CLR-7719",
                receivedByGuard = "Roberto Gómez",
                receivedTimestamp = "Hoy, 11:00 AM",
                guardNote = "Correspondencia fibra óptica. En casillero 104.",
                hasPhoto = true
            ),
            PhysicalReceipt(
                id = "rec_5",
                serviceCategory = "Cuota de Administración",
                apartment = "Torre 1 - 302",
                status = PhysicalReceiptStatus.ENTREGADO,
                referenceCode = "REC-ADM-302",
                receivedByGuard = "Carlos Mendoza",
                receivedTimestamp = "01 Sep, 09:00 AM",
                deliveredTimestamp = "01 Sep, 12:30 PM",
                guardNote = "Estado de cuenta y circular de administración.",
                hasPhoto = true
            )
        )
    )
    val receipts: StateFlow<List<PhysicalReceipt>> = _receipts.asStateFlow()

    private val _announcements = MutableStateFlow<List<Announcement>>(
        listOf(
            Announcement(
                id = "a1",
                title = "Recepción de Recibos y Paquetería",
                content = "Todo recibo físico ingresado a portería es fotografiado y registrado de inmediato en RecepControl para avisar al residente al instante.",
                date = "Hoy",
                author = "Seguridad y Portería"
            ),
            Announcement(
                id = "a2",
                title = "Mantenimiento de Citofonía y Ascensores",
                content = "Se realizará revisión del cableado de citofonía el próximo jueves. Por favor reportar novedades en recepción.",
                date = "06 Sep",
                author = "Consejo de Administración"
            )
        )
    )
    val announcements: StateFlow<List<Announcement>> = _announcements.asStateFlow()

    fun dismissAlert() {
        _activeAlert.value = null
    }

    fun loginAsVigilante(
        email: String = "carlos.mendoza@recepcontrol.com",
        phone: String = "3104567890",
        name: String = "Carlos Mendoza"
    ) {
        val finalName = if (name.isNotBlank()) name else {
            email.substringBefore("@").replace(".", " ").split(" ").joinToString(" ") { it.replaceFirstChar(Char::titlecase) }
        }
        _currentUser.value = User(
            id = "guard_1",
            email = email.ifBlank { "carlos.mendoza@recepcontrol.com" },
            name = finalName.ifBlank { "Carlos Mendoza" },
            role = UserRole.VIGILANTE,
            apartment = null,
            phone = phone.ifBlank { "310 456 7890" },
            shift = null
        )
    }

    fun loginAsResident(
        email: String = "maria.gomez@recepcontrol.com",
        phone: String = "3201234567",
        name: String = "María Gómez",
        apartment: String = "Torre 1 - 302"
    ) {
        val finalName = if (name.isNotBlank()) name else {
            email.substringBefore("@").replace(".", " ").split(" ").joinToString(" ") { it.replaceFirstChar(Char::titlecase) }
        }
        _currentUser.value = User(
            id = "res_1",
            email = email.ifBlank { "maria.gomez@recepcontrol.com" },
            name = finalName.ifBlank { "María Gómez" },
            role = UserRole.RESIDENT,
            apartment = apartment.ifBlank { "Torre 1 - 302" },
            phone = phone.ifBlank { "320 123 4567" },
            shift = null
        )
    }

    fun updateProfile(name: String, email: String, phone: String, apartment: String? = null) {
        _currentUser.value?.let { current ->
            _currentUser.value = current.copy(
                name = name,
                email = email,
                phone = phone,
                apartment = apartment ?: current.apartment
            )
        }
    }

    fun logout() {
        _currentUser.value = null
        _activeAlert.value = null
    }

    /**
     * Guard flow: registers a receipt or building event (elevators, pool, common areas, package).
     * ZERO money/price fields!
     * Automatically triggers a notification for the resident or whole building!
     */
    fun registerReceipt(serviceCategory: String, apartment: String, guardNote: String) {
        val guardName = _currentUser.value?.name ?: "Carlos Mendoza"
        val newReceipt = PhysicalReceipt(
            id = "rec_${System.currentTimeMillis()}",
            serviceCategory = serviceCategory,
            apartment = apartment,
            status = PhysicalReceiptStatus.EN_PORTERIA,
            referenceCode = "REC-${(100000..999999).random()}",
            receivedByGuard = guardName,
            receivedTimestamp = "Hoy, recién recibido",
            guardNote = guardNote.ifBlank { "Registrado en bitácora de portería." },
            hasPhoto = true
        )
        _receipts.value = listOf(newReceipt) + _receipts.value

        // Trigger resident notification
        val isGeneralEvent = apartment.contains("Zonas Comunes", ignoreCase = true) || 
                             apartment.contains("Edificio", ignoreCase = true) ||
                             serviceCategory.contains("Mantenimiento", ignoreCase = true)

        val notification = ResidentNotification(
            id = "notif_${System.currentTimeMillis()}",
            title = if (isGeneralEvent) "¡Aviso de Mantenimiento / Evento!" else "¡Nuevo Recibo / Evento en Portería!",
            message = "El guarda $guardName ha registrado $serviceCategory para $apartment: ${guardNote.take(70)}...",
            timestamp = "Ahora mismo",
            apartment = apartment,
            receiptId = newReceipt.id
        )
        _notifications.value = listOf(notification) + _notifications.value
        _activeAlert.value = notification

        if (isGeneralEvent) {
            val announcement = Announcement(
                id = "ann_${System.currentTimeMillis()}",
                title = serviceCategory,
                content = guardNote.ifBlank { "Evento de mantenimiento registrado por portería para $apartment." },
                date = "Hoy",
                author = "Portería ($guardName)"
            )
            _announcements.value = listOf(announcement) + _announcements.value
        }
    }

    fun markAsDelivered(receiptId: String) {
        _receipts.value = _receipts.value.map {
            if (it.id == receiptId) {
                it.copy(
                    status = PhysicalReceiptStatus.ENTREGADO,
                    deliveredTimestamp = "Hoy, entregado en puerta"
                )
            } else it
        }
    }

    fun postAnnouncement(title: String, content: String) {
        val newAnn = Announcement(
            id = "ann_${System.currentTimeMillis()}",
            title = title,
            content = content,
            date = "Hoy",
            author = _currentUser.value?.name ?: "Portería"
        )
        _announcements.value = listOf(newAnn) + _announcements.value
    }
}
