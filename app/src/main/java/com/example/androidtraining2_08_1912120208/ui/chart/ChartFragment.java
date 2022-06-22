package com.example.androidtraining2_08_1912120208.ui.chart;//package com.example.androidtraining2_08_1912120208.ui.chart;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.navigation.Navigation;
//
//import com.example.androidtraining2_08_1912120208.R;
//import com.example.androidtraining2_08_1912120208.bean.BoomMenuItemBean;
//import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
//import com.nightonke.boommenu.BoomMenuButton;
//public class ChartFragment extends Fragment {
//
//    private ChartViewModel chartViewModel;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//         chartViewModel =
//                new ViewModelProvider(this).get(ChartViewModel.class);
//            View root=inflater.inflate(R.layout.fragment_chart,container,false);
//        BoomMenuButton bmb = (BoomMenuButton)root.findViewById(R.id.bmb);
//        chartViewModel.getBoomMenuItemList().observe(getViewLifecycleOwner(),boomMenuItemBeans -> {
//            for (BoomMenuItemBean boomMenuItemBean:boomMenuItemBeans) {
//                TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder();
//                builder.normalText(boomMenuItemBean.getTitle())
//                        .normalImageRes(boomMenuItemBean.getImageId())
//                        .listener(index -> {
//                            switch (index){
//                                case 0:
//                                    Navigation.findNavController(root).navigate(
//                                            R.id.action_navigation_chart_to_lineFragment
//                                    );
//                                    break;
//                                case 1:
//                                    Navigation.findNavController(root).navigate(
//                                            R.id.action_navigation_chart_to_barFragment
//                                    );
//                                    break;
//                                case 2:
//                                    Navigation.findNavController(root).navigate(
//                                            R.id.action_navigation_chart_to_pieFragment
//                                    );
//                                    break;
//
//                            }
//                        });
//                bmb.addBuilder(builder);
//            }
//        });
//        return root;
//    }
//
//
//}