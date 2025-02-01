package com.example.joaovitor_atividade06_cliente.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.joaovitor_atividade06_cliente.View.Fragments.ListaFragment;
import com.example.joaovitor_atividade06_cliente.View.Fragments.MediaFragment;

public class ViewPagerAdapter extends FragmentStateAdapter
{

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity)
    {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position)
    {
        switch (position){
            case 0: return new ListaFragment();
            case 1 : return new MediaFragment();
            default: return new ListaFragment();
        }
    }

    @Override
    public int getItemCount()
    {
        return 2;
    }
}
