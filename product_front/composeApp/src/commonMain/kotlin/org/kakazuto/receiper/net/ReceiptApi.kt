package org.kakazuto.receiper.net

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import org.kakazuto.receiper.model.Receipt
import org.kakazuto.receiper.utils.createUUID

interface ReceiptApi {
    suspend fun uploadReceipt(userId: Int, receipt: ByteArray): String?
    suspend fun setPathOnDB(userId: Int, path: String)
}

internal class ReceiptApiImpl(
    private val client : SupabaseClient
): ReceiptApi {
    private val table = client.postgrest["receipt_dev"]


    override suspend fun uploadReceipt(userId: Int, receipt: ByteArray): String {
        val path = "${userId}/${createUUID()}.jpg"
        val bucket = client.storage["receipts"]
        // add error handling and logging
        try {
            bucket.upload(path, receipt)
        } catch (e: Exception) {
            e.printStackTrace()
        }

       return path
    }

    override suspend fun setPathOnDB(userId: Int, path:String) {
        table.insert(Receipt(userId, path), upsert = false)
    }

}