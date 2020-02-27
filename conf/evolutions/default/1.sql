# --- !Ups

CREATE TABLE [dbo].[country](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[iso_code] [nvarchar](255) NOT NULL,
	[created] [datetime] NOT NULL,
 CONSTRAINT [pk_country] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [uq_country_iso_code] UNIQUE NONCLUSTERED
(
	[iso_code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY];


CREATE TABLE [dbo].[customer](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[email] [nvarchar](255) NULL,
	[name] [nvarchar](255) NOT NULL,
	[country_id] [bigint] NOT NULL,
	[language] [nvarchar](255) NOT NULL,
	[created] [datetime] NOT NULL,
	[last_updated] [datetime] NOT NULL,
 CONSTRAINT [pk_customer] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY];

ALTER TABLE [dbo].[customer]  WITH CHECK ADD  CONSTRAINT [fk_customer_country_44] FOREIGN KEY([country_id])
REFERENCES [dbo].[country] ([id]);

ALTER TABLE [dbo].[customer] CHECK CONSTRAINT [fk_customer_country_44];


CREATE TABLE [dbo].[customer_group](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
	[country_id] [bigint] NOT NULL,
	[created] [datetime] NOT NULL,
 CONSTRAINT [pk_customer_group] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [uq_customer_group_1] UNIQUE NONCLUSTERED
(
	[name] ASC,
	[country_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY];

ALTER TABLE [dbo].[customer_group]  WITH CHECK ADD  CONSTRAINT [fk_customer_group_country_42] FOREIGN KEY([country_id])
REFERENCES [dbo].[country] ([id]);

ALTER TABLE [dbo].[customer_group] CHECK CONSTRAINT [fk_customer_group_country_42];


CREATE TABLE [dbo].[customer_group_relation](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[customer_id] [bigint] NOT NULL,
	[customer_group_id] [bigint] NOT NULL,
	[customer_number] [nvarchar](255) NOT NULL,
	[created] [datetime] NOT NULL,
 CONSTRAINT [pk_customer_group_relation] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY];

ALTER TABLE [dbo].[customer_group_relation]  WITH CHECK ADD  CONSTRAINT [fk_customer_group_rel_group] FOREIGN KEY([customer_group_id])
REFERENCES [dbo].[customer_group] ([id]);

ALTER TABLE [dbo].[customer_group_relation] CHECK CONSTRAINT [fk_customer_group_rel_group];

ALTER TABLE [dbo].[customer_group_relation]  WITH CHECK ADD  CONSTRAINT [fk_customer_group_rel_customer] FOREIGN KEY([customer_id])
REFERENCES [dbo].[customer] ([id]);

ALTER TABLE [dbo].[customer_group_relation] CHECK CONSTRAINT [fk_customer_group_rel_customer];



CREATE TABLE [dbo].[point_balance](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[customer_id] [bigint] NOT NULL,
	[is_expired] [bit] NOT NULL,
	[remaining_points] [decimal](28, 2) NOT NULL,
	[created] [datetime] NOT NULL,
 CONSTRAINT [pk_point_balance] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY];

ALTER TABLE [dbo].[point_balance]  WITH CHECK ADD  CONSTRAINT [fk_point_balance_customer] FOREIGN KEY([customer_id])
REFERENCES [dbo].[customer] ([id]);

ALTER TABLE [dbo].[point_balance] CHECK CONSTRAINT [fk_point_balance_customer];


CREATE TABLE [dbo].[scan_unsubmitted](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[customer_id] [bigint] NULL,
	[quantity] [bigint] NULL,
	[value] [nvarchar](255) NOT NULL,
	[created] [datetime] NOT NULL,
 CONSTRAINT [pk_scan_unsubmitted] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY];

ALTER TABLE [dbo].[scan_unsubmitted]  WITH CHECK ADD  CONSTRAINT [fk_scan_unsubmitted_customer] FOREIGN KEY([customer_id])
REFERENCES [dbo].[customer] ([id]);

ALTER TABLE [dbo].[scan_unsubmitted] CHECK CONSTRAINT [fk_scan_unsubmitted_customer];


# --- !Downs

drop table scan_unsubmitted;

drop table point_balance;

drop table customer_group_relation;

drop table customer_group;

drop table customer;

drop table country;
