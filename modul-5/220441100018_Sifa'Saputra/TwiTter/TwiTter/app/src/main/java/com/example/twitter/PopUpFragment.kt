package com.example.twitter


import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.twitter.Room.ItemDatabase
import com.example.twitter.Room.ItemViewModel
import com.example.twitter.Room.ItemViewModelFactory
import com.google.android.material.button.MaterialButton

// Kelas PopUpFragment adalah kelas yang mewarisi DialogFragment.
// Kelas ini digunakan untuk menampilkan dialog pop-up dengan opsi untuk mengubah atau menghapus pemain.
class PopUpFragment(private val playerEntity: ItemDatabase, position: Int) : DialogFragment() {

    // Mendeklarasikan ViewModel untuk interaksi dengan database
    private lateinit var appViewModel: ItemViewModel

    // Fungsi getTheme digunakan untuk mendapatkan tema dialog.
    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    // Fungsi onStart dipanggil ketika dialog dimulai.
    // Fungsi ini digunakan untuk mengatur layout dialog.
    override fun onStart() {
        super.onStart()
        requireDialog().window?.apply {
            setLayout(WRAP_CONTENT, WRAP_CONTENT)
        }

        view?.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(16, 16, 16, 16)
        }
    }

    // Fungsi onCreateView digunakan untuk membuat tampilan dialog.
    // Fungsi ini mengembalikan tampilan yang dibuat dari layout fragment_pop_up.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_pop_up, container, false)
    }

    // Fungsi onViewCreated dipanggil setelah tampilan dialog dibuat.
    // Fungsi ini digunakan untuk menginisialisasi ViewModel dan menangani aksi klik pada tombol ubah dan hapus.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mendapatkan instance ViewModel.
        val factory = ItemViewModelFactory.getInstance(requireContext())
        appViewModel = ViewModelProvider(this, factory)[ItemViewModel::class.java]

        // Mendapatkan referensi ke tombol ubah dan hapus.
        val btnUbah: MaterialButton = view.findViewById(R.id.btn_ubah)
        val btnHapus: MaterialButton = view.findViewById(R.id.btn_hapus)

        // Menangani aksi klik pada tombol ubah.
        // Ketika tombol ubah diklik, intent baru dibuat untuk memulai UpdatePlayerRoomActivity dan pemain yang dipilih dikirim sebagai extra.
        btnUbah.setOnClickListener {
            val intent = Intent(requireContext(), update::class.java).apply {
                putExtra("player", playerEntity)
            }
            startActivity(intent)
            dismiss()
        }


        // Menangani aksi klik pada tombol hapus.
        // Ketika tombol hapus diklik, pemain yang dipilih dihapus dari database dan dialog ditutup.
        btnHapus.setOnClickListener {
            appViewModel.deleteItem(playerEntity)
            dismiss()
        }
    }

    // Objek companion yang berisi tag untuk dialog.
    companion object {
        const val TAG = "PopUpFragment"
    }
}