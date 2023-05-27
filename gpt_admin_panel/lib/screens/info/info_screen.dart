import 'package:flutter/material.dart';
import 'package:gpt_admin_panel/models/contact.dart';
import 'package:gpt_admin_panel/models/plan.dart';
import 'package:gpt_admin_panel/models/question.dart';
import 'package:gpt_admin_panel/models/subscriptions.dart';
import 'package:gpt_admin_panel/screens/info/info_presenter.dart';
import 'package:gpt_admin_panel/ui/components/atoms/bordered_container.dart';
import 'package:gpt_admin_panel/ui/components/atoms/headline_medium.dart';
import 'package:gpt_admin_panel/ui/components/atoms/title_large.dart';
import 'package:gpt_admin_panel/ui/constants/app_colors.dart';

enum InfoType { users, messages, plans, subscriptions }

class InfoScreen extends StatefulWidget {
  final InfoType type;
  final InfoPresenter presenter = InfoPresenterImpl();

  InfoScreen({Key? key, required this.type}) : super(key: key);

  @override
  State<InfoScreen> createState() => _InfoScreenState();
}

class _InfoScreenState extends State<InfoScreen> {
  String get title {
    switch (widget.type) {
      case InfoType.users:
        return 'Usuários';
      case InfoType.messages:
        return 'Perguntas';
      case InfoType.plans:
        return 'Planos';
      case InfoType.subscriptions:
        return 'Assinaturas';
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: AppColors.darkBG,
      ),
      backgroundColor: AppColors.darkBG,
      body: BorderedContainer(
        child: Center(child: buildListForType()),
      ),
    );
  }

  Widget buildListForType() {
    switch (widget.type) {
      case InfoType.messages:
        return buildMessageList();
      case InfoType.users:
        return buildUsersList();
      case InfoType.plans:
        return buildPlansList();
      case InfoType.subscriptions:
        return buildSubscriptionsList();
    }
  }

  Widget buildMessageList() {
    return FutureBuilder(
      future: widget.presenter.getQuestions(),
      builder: (BuildContext context, AsyncSnapshot<QuestionList> snapshot) {
        if (snapshot.hasData) {
          final questionList = snapshot.data!;
          return Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const HeadlineMedium('PowerChat GPT - Admin'),
              TitleLarge(title),
              const Divider(),
              Flexible(
                child: ListView.separated(
                    itemBuilder: (BuildContext context, int index) {
                      final question = questionList.data.elementAt(index);
                      final prompt = question.prompt;
                      final reply = question.reply;
                      return ListTile(
                        title: Text(prompt),
                        subtitle: Text(reply),
                      );
                    },
                    separatorBuilder: (BuildContext context, int index) {
                      return const Divider();
                    },
                    itemCount: questionList.data.length),
              ),
            ],
          );
        } else if (snapshot.hasError) {
          return const Text('Error on query');
        } else {
          return const CircularProgressIndicator();
        }
      },
    );
  }

  Widget buildUsersList() {
    return FutureBuilder(
      future: widget.presenter.getUsers(),
      builder: (BuildContext context, AsyncSnapshot<ContactList> snapshot) {
        if (snapshot.hasData) {
          final contactList = snapshot.data!;
          return Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const HeadlineMedium('PowerChat GPT - Admin'),
              TitleLarge(title),
              const Divider(),
              Flexible(
                child: ListView.separated(
                    itemBuilder: (BuildContext context, int index) {
                      final contact = contactList.data.elementAt(index);
                      final email = contact.email;
                      final name = contact.name;
                      return ListTile(
                        title: Text(email),
                        subtitle: Text(name),
                      );
                    },
                    separatorBuilder: (BuildContext context, int index) {
                      return const Divider();
                    },
                    itemCount: contactList.data.length),
              ),
            ],
          );
        } else if (snapshot.hasError) {
          return const Text('Error on query');
        } else {
          return const CircularProgressIndicator();
        }
      },
    );
  }

  Widget buildPlansList() {
    return FutureBuilder(
      future: widget.presenter.getPlans(),
      builder: (BuildContext context, AsyncSnapshot<PlanList> snapshot) {
        if (snapshot.hasData) {
          final planList = snapshot.data!;
          return Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const HeadlineMedium('PowerChat GPT - Admin'),
              TitleLarge(title),
              const Divider(),
              Flexible(
                child: ListView.separated(
                    itemBuilder: (BuildContext context, int index) {
                      final plan = planList.data.elementAt(index);
                      // final id = plan.id;
                      final name = plan.name;
                      final monthLimit = plan.monthlyPromptLimit;
                      return ListTile(
                        title: Text(name),
                        subtitle: Text('Monthly prompt limit: $monthLimit'),
                      );
                    },
                    separatorBuilder: (BuildContext context, int index) {
                      return const Divider();
                    },
                    itemCount: planList.data.length),
              ),
            ],
          );
        } else if (snapshot.hasError) {
          return const Text('Error on query');
        } else {
          return const CircularProgressIndicator();
        }
      },
    );
  }

  Widget buildSubscriptionsList() {
    return FutureBuilder(
      future: widget.presenter.getSubscriptions(),
      builder:
          (BuildContext context, AsyncSnapshot<SubscriptionList> snapshot) {
        if (snapshot.hasData) {
          final subscriptionList = snapshot.data!;
          return Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const HeadlineMedium('PowerChat GPT - Admin'),
              TitleLarge(title),
              const Divider(),
              Flexible(
                child: ListView.separated(
                    itemBuilder: (BuildContext context, int index) {
                      final subscription =
                          subscriptionList.data.elementAt(index);
                      final id = subscription.id;
                      final plan = subscription.planId;
                      final userId = subscription.userId;

                      return ListTile(
                          title: Text(id.toString()),
                          trailing: IconButton(
                            icon: const Icon(Icons.delete_forever),
                            onPressed: () {
                              widget.presenter
                                  .deleteSubscription(id)
                                  .then((_) => setState(() {}));
                            },
                          ),
                          subtitle: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text('Subscription plan: $plan'),
                              Text('User id: $userId'),
                            ],
                          ));
                    },
                    separatorBuilder: (BuildContext context, int index) {
                      return const Divider();
                    },
                    itemCount: subscriptionList.data.length),
              ),
            ],
          );
        } else if (snapshot.hasError) {
          return const Text('Error on query');
        } else {
          return const CircularProgressIndicator();
        }
      },
    );
  }
}
