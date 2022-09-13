package com.example.navegacion

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.room.Room
import com.example.navegacion.DB.Registro
import com.example.navegacion.DB.RegistroDao
import com.example.navegacion.DB.RegistroDatabase
import com.example.navegacion.databinding.FragmentFavoritosBinding
import com.example.navegacion.databinding.FragmentInicioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import android.widget.ArrayAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentInicio.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentInicio : Fragment() {
    private lateinit var binding: FragmentInicioBinding

    private val db by lazy{
        context?.let {
            Room.databaseBuilder(
                it,
                RegistroDatabase::class.java,
                "registro_database"
            ).build()
        }
    }
    private lateinit var regDao: RegistroDao

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInicioBinding.inflate(inflater, container, false)
        regDao = db!!.registroDao()

        binding.btnString.setOnClickListener{
           insertRegistro()
        }
        showregistros()
        return binding.root

    }
    private fun insertRegistro() {
        var gender=""
        if(binding.rbF.isChecked){
            gender= "F"
        }else if(binding.rbM.isChecked){
            gender = "M"
        }
        var newReg=Registro(0,
            binding.etName.text.toString(),
            binding.etLastname.text.toString(),
            gender
        )
        CoroutineScope(Dispatchers.IO).launch {
            regDao.insert(newReg)
        }
        println("agregado con exito")
        binding.etName.text=null
        binding.etLastname.text=null

    }
    private fun showregistros(){
        var registros: MutableList<Registro>
        var datos=ArrayList<String>()
        CoroutineScope(Dispatchers.IO).launch {
                    registros = regDao.getRegistros()
                    if(registros.isEmpty()){
                        println("Registros vacios")
                    }else{
                        var adapter: ArrayAdapter<String> = ArrayAdapter(activity as Context,
                            R.layout.item_lista,R.id.textViewItem, datos)
                        binding.lvRegistros.adapter = adapter
                        for (dato in registros){
                            datos.add(dato.nombre + " " + dato.apellido)
                            adapter.notifyDataSetChanged()
                        }
                        deleteRegistro(adapter,datos, registros)
                    }
        }

    }
    private fun deleteRegistro(adapter: ArrayAdapter<String>, datos:ArrayList<String>, registros: MutableList<Registro> ){
            binding.lvRegistros.setOnItemClickListener { adapterView, view, i, l ->
                var item=datos.get(i)
                var itemdb = registros.get(i)
                println(itemdb)
                CoroutineScope(Dispatchers.IO).launch{
                    regDao.deleteN(itemdb.nombre)
                }
                datos.remove(item)
                adapter.notifyDataSetChanged()
                println("eliminado con exito")
            }
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentInicio.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentInicio().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}