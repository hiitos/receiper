package org.kakazuto.receiper.net

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import org.kakazuto.receiper.utils.createUUID

interface ReceiptApi {
    suspend fun createBucket(userId: Int)
    suspend fun uploadReceipt(userId: Int, receipt: ByteArray): String?
    suspend fun setPathOnDB(userId: Int, path: String)
}

internal class ReceiptApiImpl(
    private val client : SupabaseClient
): ReceiptApi {
    private val table = client.postgrest["receipt"]

    override suspend fun createBucket(userId: Int) {
        client.storage.createBucket(userId.toString()) {
            public = true
            fileSizeLimit = 30.megabytes
        }
    }

    override suspend fun uploadReceipt(userId: Int, receipt: ByteArray): String? {
//        storage
//        val receiptId = createUUID()
//        val result = table.insert(Receipt(receiptId, userId, receipt)).decodeSingle<Receipt>()
        return ""
    }

    override suspend fun setPathOnDB(userId: Int, path:String) {
//        table.update({
//            Receipt::path setTo path
//        }) {
//            Receipt::user_id eq userId
//        }
    }
}