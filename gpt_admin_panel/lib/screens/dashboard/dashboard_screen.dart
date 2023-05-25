import 'package:flutter/material.dart';
import 'package:gpt_admin_panel/screens/dashboard/dashboard_presenter.dart';
import 'package:gpt_admin_panel/screens/info/info_screen.dart';
import 'package:gpt_admin_panel/ui/components/atoms/bordered_container.dart';
import 'package:gpt_admin_panel/ui/components/atoms/headline_medium.dart';
import 'package:gpt_admin_panel/ui/components/atoms/title_large.dart';
import 'package:gpt_admin_panel/ui/components/atoms/title_medium.dart';
import 'package:gpt_admin_panel/ui/components/molecules/medium_card.dart';
import 'package:gpt_admin_panel/ui/constants/app_colors.dart';

class DashboardScreen extends StatefulWidget {
  final DashboardPresenter presenter;

  const DashboardScreen({Key? key, required this.presenter}) : super(key: key);

  @override
  State<DashboardScreen> createState() => _DashboardScreenState();
}

class _DashboardScreenState extends State<DashboardScreen> {
  @override
  void initState() {
    widget.presenter.listen(() {
      setState(() {});
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.darkBG,
      body: BorderedContainer(
        child: Center(
          child: SingleChildScrollView(
            child: dataFields(context),
          ),
        ),
      ),
    );
  }

  List<Widget> serviceMetrics(BuildContext context) {
    var healthState = widget.presenter.healthStatus;
    return [
      const TitleLarge('Métricas de serviço'),
      Row(
        children: [
          MediumCard(
              text: 'Health check: $healthState',
              onTap: () {
                widget.presenter.checkHealth().then((_) => setState(() {}));
              }),
          MediumCard(text: 'OpenAI integration: ok', onTap: () {}),
          MediumCard(text: 'FB Webhook: ok', onTap: () {}),
          MediumCard(text: 'FB GraphQL: ok', onTap: () {}),
        ],
      ),
    ];
  }

  List<Widget> usageMetrics(BuildContext context) {
    return [
      const TitleLarge('Métricas de uso'),
      Padding(
        padding: EdgeInsets.symmetric(horizontal: 12.0),
        child: FutureBuilder(
          future: Future.wait([widget.presenter.fetchUsersNumber()]),
          builder: (BuildContext context, AsyncSnapshot<dynamic> snapshot) {
            return Row(
              children: const [
                TitleMedium('Usuários ativos: 2932'),
                TitleMedium('Requisições p/ min: 64'),
                TitleMedium('Usuários pagantes: 1294'),
              ],
            );
          },
        ),
      ),
    ];
  }

  Widget dataFields(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        const HeadlineMedium('PowerChat GPT - Admin'),
        ...usageMetrics(context),
        const SizedBox(
          height: 40,
        ),
        ...serviceMetrics(context),
        const SizedBox(
          height: 40,
        ),
        const TitleLarge('Informações'),
        Row(
          children: [
            MediumCard(
                text: 'Usuários',
                onTap: () {
                  Navigator.of(context)
                      .push(MaterialPageRoute(builder: (BuildContext context) {
                    return InfoScreen(type: InfoType.users);
                  }));
                }),
            MediumCard(
                text: 'Perguntas',
                onTap: () {
                  Navigator.of(context)
                      .push(MaterialPageRoute(builder: (BuildContext context) {
                    return InfoScreen(type: InfoType.messages);
                  }));
                }),
          ],
        ),
      ],
    );
  }
}
