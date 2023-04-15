package com.sivan.blade.ui.contacts.details

import androidx.lifecycle.ViewModel
import com.alexstyl.contactstore.Contact
import com.alexstyl.contactstore.ContactPredicate.ContactIdLookup
import com.alexstyl.contactstore.ContactStore
import com.sivan.blade.ui.contacts.ContactsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

data class DetailsViewState(
    val data: DetailsState = DetailsState.Idle
)

sealed class DetailsState {
    object Idle : DetailsState()
    object Loading : DetailsState()
    data class Success(val data: Contact) : DetailsState()
    object EmptyList : DetailsState()
}

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val contactStore: ContactStore
) : ContainerHost<DetailsViewState, Nothing>,
    ViewModel() {
    override val container: Container<DetailsViewState, Nothing> = container(DetailsViewState())

    fun getContact(contactId: String) = intent {

        reduce {
            state.copy(
                data = DetailsState.Loading
            )
        }

        val res =
            contactStore.fetchContacts(predicate = ContactIdLookup(contactId = contactId.toLong()))
                .blockingGet()

        if (res.isEmpty()) {
            reduce {
                state.copy(
                    data = DetailsState.EmptyList
                )
            }
        } else {
            reduce {
                state.copy(
                    data = DetailsState.Success(res.first())
                )
            }
        }
    }
}