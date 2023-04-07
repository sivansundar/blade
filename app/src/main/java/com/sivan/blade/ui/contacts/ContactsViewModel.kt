package com.sivan.blade.ui.contacts

import androidx.lifecycle.ViewModel
import com.alexstyl.contactstore.Contact
import com.alexstyl.contactstore.ContactStore
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

data class ContactsViewState(
    val data: List<Contact> = emptyList()
)

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactStore: ContactStore
) : ContainerHost<ContactsViewState, Nothing>,
    ViewModel() {
    override val container: Container<ContactsViewState, Nothing> = container(ContactsViewState())

    fun getContacts() = intent{
        val result = contactStore.fetchContacts().blockingGet()
        reduce {
            state.copy(data = result)
        }
    }
}